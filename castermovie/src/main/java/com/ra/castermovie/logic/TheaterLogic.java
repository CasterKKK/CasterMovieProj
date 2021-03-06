package com.ra.castermovie.logic;

import com.ra.castermovie.logic.common.Result;
import com.ra.castermovie.model.PublicInfo;
import com.ra.castermovie.model.RequestInfo;
import com.ra.castermovie.model.Theater;
import com.ra.castermovie.model.common.Genre;
import com.ra.castermovie.model.order.OrderState;
import com.ra.castermovie.model.theater.UserTheater;
import org.springframework.data.util.Pair;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface TheaterLogic {
    Result<RequestInfo> register(UserTheater theater);

    Result<RequestInfo> update(String id, UserTheater theater);

    Result<Theater> newPublicInfo(String theaterId, String showId, List<Instant> schedules, Integer basePrice);

    Result<List<PublicInfo>> findAllShowPlaying(String theaterId, Pair<Instant, Instant> timePair, List<Genre> genreList);

    Result<List<Theater>> findAllTheater(int regionId);

    Result<Theater> findById(String id);

    Result<Map<String, Integer>> bigFiveTotal(String theaterId);

    Result<Map<OrderState, Integer>> orderStatesTotal(String theaterId);

    Result<Integer> grossIncomeMonthly(String yearMonthString, String theaterId);

    Result<Map<String, Integer>> grossIncomeMonthlyRange(List<String> yearMonths, String theaterId);
}
