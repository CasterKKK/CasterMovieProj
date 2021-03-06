package com.ra.castermovie.controller;

import com.ra.castermovie.controller.vo.show.NewShowVO;
import com.ra.castermovie.logic.ShowLogic;
import com.ra.castermovie.logic.common.Result;
import com.ra.castermovie.model.Show;
import com.ra.castermovie.model.common.Genre;
import com.ra.castermovie.model.show.WillPlayShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/castermovie/show")
public class ShowController {
    @Autowired
    private ShowLogic showLogic;

    @PostMapping(value = "newShow", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<Show> newShow(@RequestBody NewShowVO vo) {
        return showLogic.newShow(vo.getName(), vo.getGenre(), vo.getDuration(), vo.getPoster());
    }

    @GetMapping(value = "findAllByGenreIn", consumes = MediaType.ALL_VALUE)
    Result<List<Show>> findAllByGenreIn(@RequestParam(required = false) List<Genre> genreList) {
        return showLogic.findAllByGenreIn(genreList);
    }

    @GetMapping(value = "findAllByGenreInAndStartTime", consumes = MediaType.ALL_VALUE)
    Result<List<Show>> findAllByGenreInAndStartTime(@RequestParam(required = false) List<Genre> genreList, @RequestParam Long startTime) {
        return showLogic.findAllByGenreInAndStartTime(genreList, startTime);
    }

    @GetMapping(value = "findById", consumes = MediaType.ALL_VALUE)
    Result<Show> findById(@RequestParam String id) {
        return showLogic.findById(id);
    }

    @GetMapping(value = "findAllPlayingNow")
    Result<List<Show>> findAllPlayingNow() {
        return showLogic.findAllPlayingNow(null);
    }

    @GetMapping(value = "findAllWillPlay")
    Result<List<WillPlayShow>> findAllWillPlay() {
        return showLogic.findAllWillPlay(null);
    }

    @GetMapping(value = "findAllPlayingNowInRegion")
    Result<Set<Show>> findAllPlayingNowInRegion(@RequestParam int regionId) {
        return showLogic.findAllPlayingNowInRegion(regionId);
    }

    @GetMapping(value = "findAllWillPlayInRegion")
    Result<Set<WillPlayShow>> findAllWillPlayInRegion(@RequestParam int regionId) {
        return showLogic.findAllWillPlayInRegion(regionId);
    }

}
