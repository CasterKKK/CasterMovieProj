package com.ra.castermovie.controller;

import com.ra.castermovie.controller.vo.theater.FindAllShowPlayingVO;
import com.ra.castermovie.controller.vo.theater.NewPublicInfoVO;
import com.ra.castermovie.controller.vo.theater.RegisterVO;
import com.ra.castermovie.controller.vo.theater.UpdateVO;
import com.ra.castermovie.logic.ShowLogic;
import com.ra.castermovie.logic.TheaterLogic;
import com.ra.castermovie.logic.common.Result;
import com.ra.castermovie.model.PublicInfo;
import com.ra.castermovie.model.RequestInfo;
import com.ra.castermovie.model.Show;
import com.ra.castermovie.model.Theater;
import com.ra.castermovie.model.order.OrderState;
import com.ra.castermovie.model.show.WillPlayShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/castermovie/theater")
public class TheaterController {
    @Autowired
    private TheaterLogic theaterLogic;
    @Autowired
    private ShowLogic showLogic;

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<RequestInfo> register(@RequestBody RegisterVO vo) {
        return theaterLogic.register(vo.toUserTheater());
    }

    @PostMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<RequestInfo> update(@RequestBody UpdateVO vo) {
        return theaterLogic.update(vo.getId(), vo.getUserTheater());
    }

    @PostMapping(value = "newPublicInfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<Theater> newPublicInfo(@RequestBody NewPublicInfoVO vo) {
        return theaterLogic.newPublicInfo(vo.getTheaterId(), vo.getShowId(), vo.getSchedules(), vo.getBasePrice());
    }

    @GetMapping(value = "findAllShowPlaying", consumes = MediaType.ALL_VALUE)
    Result<List<PublicInfo>> findAllShowPlaying(@ModelAttribute FindAllShowPlayingVO vo) {
        Instant from = vo.getTimeFrom() == null ? Instant.MIN : Instant.ofEpochMilli(vo.getTimeFrom());
        Instant to = vo.getTimeTo() == null ? Instant.MAX : Instant.ofEpochMilli(vo.getTimeTo());
        Pair<Instant, Instant> timePair = Pair.of(from, to);
        return theaterLogic.findAllShowPlaying(vo.getTheaterId(), timePair, vo.getGenreList());
    }

    @GetMapping(value = "findAllShowPlayingNow")
    Result<List<Show>> findAllPlayingNow(@RequestParam String id) {
        return showLogic.findAllPlayingNow(id);
    }

    @GetMapping(value = "findAllShowWillPlay")
    Result<List<WillPlayShow>> findAllWillPlay(@RequestParam String id) {
        return showLogic.findAllWillPlay(id);
    }


    @GetMapping(value = "findAllTheater", consumes = MediaType.ALL_VALUE)
    Result<List<Theater>> findAllTheater(@RequestParam int regionId) {
        return theaterLogic.findAllTheater(regionId);
    }

    @GetMapping(value = "findById", consumes = MediaType.ALL_VALUE)
    Result<Theater> findById(@RequestParam String id) {
        return theaterLogic.findById(id);
    }

    @GetMapping(value = "bigFiveTotal", consumes = MediaType.ALL_VALUE)
    Result<Map<String, Integer>> bigFiveTotal(@RequestParam String theaterId) {
        return theaterLogic.bigFiveTotal(theaterId);
    }

    @GetMapping(value = "orderStatesTotal", consumes = MediaType.ALL_VALUE)
    Result<Map<OrderState, Integer>> orderStatesTotal(@RequestParam String theaterId) {
        return theaterLogic.orderStatesTotal(theaterId);
    }

    @GetMapping(value = "grossIncomeMonthly", consumes = MediaType.ALL_VALUE)
    Result<Integer> grossIncomeMonthly(@RequestParam String yearMonth, @RequestParam String theaterId) {
        return theaterLogic.grossIncomeMonthly(yearMonth, theaterId);
    }

    @GetMapping(value = "grossIncomeMonthlyRange", consumes = MediaType.ALL_VALUE)
    Result<Map<String, Integer>> grossIncomeMonthlyRange(@RequestParam List<String> yearMonths, @RequestParam String theaterId) {
        return theaterLogic.grossIncomeMonthlyRange(yearMonths, theaterId);
    }
}
