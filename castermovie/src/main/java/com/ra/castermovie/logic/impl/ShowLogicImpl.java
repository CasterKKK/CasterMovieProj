package com.ra.castermovie.logic.impl;

import com.ra.castermovie.logic.ShowLogic;
import com.ra.castermovie.logic.common.Result;
import com.ra.castermovie.model.PublicInfo;
import com.ra.castermovie.model.Show;
import com.ra.castermovie.model.Theater;
import com.ra.castermovie.model.common.Genre;
import com.ra.castermovie.model.show.WillPlayShow;
import com.ra.castermovie.service.PublicInfoService;
import com.ra.castermovie.service.ShowService;
import com.ra.castermovie.service.TheaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class ShowLogicImpl implements ShowLogic {
    private final ShowService showService;
    private final PublicInfoService publicInfoService;
    private final TheaterService theaterService;

    @Autowired
    public ShowLogicImpl(ShowService showService, PublicInfoService publicInfoService, TheaterService theaterService) {
        this.showService = showService;
        this.publicInfoService = publicInfoService;
        this.theaterService = theaterService;
    }

    @Override
    public Result<Show> newShow(String name, Genre genre, int duration, String poster) {
        Show show = showService.save(new Show(name, genre, duration, poster)).block();
        return show == null ? Result.fail("影剧信息保存失败") : Result.succeed(show);
    }

    @Override
    public Result<List<Show>> findAllByGenreIn(List<Genre> genreList) {
        return Result.succeed(showService.findAllByGenreIn(genreList).collectList().block());
    }

    @Override
    public Result<List<Show>> findAllByGenreInAndStartTime(List<Genre> genreList, Long startTime) {
        return Result.succeed(showService.findAllByGenreInAndStartTime(genreList, startTime).collectList().block());
    }

    @Override
    public Result<Show> findById(String id) {
        Show show = showService.findById(id).block();
        return show == null ? Result.fail("代码失效") : Result.succeed(show);
    }

    private Stream<Show> _findAllPlayingNowToStream(String theaterId) {
        long now = Instant.now().toEpochMilli();
        long endOfToday = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli();

        List<PublicInfo> piThisDay = publicInfoService.findAllByScheduleBetween(now, endOfToday).collectList().block();
        if (theaterId != null)
            piThisDay = piThisDay.stream().filter(pi -> pi.getTheaterId().equals(theaterId)).collect(Collectors.toList());
        return piThisDay.stream().map(PublicInfo::getShowId).distinct().map(id -> showService.findById(id).block());

    }

    @Override
    public Result<List<Show>> findAllPlayingNow(String theaterId) {
        return Result.succeed(_findAllPlayingNowToStream(theaterId).collect(Collectors.toList()));
    }

    private Stream<WillPlayShow> _findAllWillPlayToStream(String theaterId) {
        long now = System.currentTimeMillis();
        final long tomorrow = ((now / 86_400_000) + 1) * (86_400_000);
        final long twoWeeks = ((now / 86_400_000) + 15) * (86_400_000);

        List<PublicInfo> publicInfoTwoWeeks = publicInfoService.findAllByScheduleBetween(tomorrow, twoWeeks).collectList().block();
        if (theaterId != null)
            publicInfoTwoWeeks = publicInfoTwoWeeks.stream().filter(pi -> pi.getTheaterId().equals(theaterId)).collect(Collectors.toList());

        return publicInfoTwoWeeks.stream().map(PublicInfo::getShowId).distinct().map(showId -> {
            List<PublicInfo> publicInfos = publicInfoService.findAllByShowId(showId).collectList().block();
            long min = publicInfos.stream().map(PublicInfo::getSchedule).min(Long::compareTo).orElse(Long.MAX_VALUE);
            return Pair.of(showId, min);
        }).filter(p -> tomorrow <= p.getSecond() && p.getSecond() <= twoWeeks).map(p -> new WillPlayShow(showService.findById(p.getFirst()).block(), p.getSecond()));
    }

    @Override
    public Result<List<WillPlayShow>> findAllWillPlay(String theaterId) {
        return Result.succeed(_findAllWillPlayToStream(theaterId).collect(Collectors.toList()));
    }

    @Override
    public Result<Set<Show>> findAllPlayingNowInRegion(int regionId) {
        List<String> theatersInRegion = theaterService.findAllByRegionId(regionId).collectList().block().stream().map(Theater::getId).collect(Collectors.toList());
        return Result.succeed(theatersInRegion.stream().flatMap(this::_findAllPlayingNowToStream).collect(Collectors.toSet()));
    }

    @Override
    public Result<Set<WillPlayShow>> findAllWillPlayInRegion(int regionId) {
        List<String> theatersInRegion = theaterService.findAllByRegionId(regionId).collectList().block().stream().map(Theater::getId).collect(Collectors.toList());
        return Result.succeed(theatersInRegion.stream().flatMap(this::_findAllWillPlayToStream).collect(Collectors.toSet()));
    }

}
