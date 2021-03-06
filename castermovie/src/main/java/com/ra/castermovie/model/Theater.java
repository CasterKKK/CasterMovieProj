package com.ra.castermovie.model;


import com.ra.castermovie.model.common.Condition;
import com.ra.castermovie.model.theater.UserTheater;
import com.ra.castermovie.model.user.Level;
import com.ra.castermovie.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "theater")
public class Theater {
    public static final String DEFAULT_AVATAR = "https://castermovie.oss-cn-beijing.aliyuncs.com/theater/theater_bg.jpg";

    @Id
    // id is email
    private String id;
    @NotNull(message = "Theater::condition must not be null")
    private Condition condition;
    @NotNull(message = "Theater::condition must not be null")
    private Role role;
    @NotNull(message = "Theater::password must not be null")
    private String password;
    @NotNull(message = "Theater::name must not be null")
    private String name;
    @NotNull(message = "Theater::regionId must not be null")
    private Integer regionId;
    @NotNull(message = "Theater::location must not be null")
    private String location;
    @NotNull(message = "Theater::seatNumber must not be null")
    private Integer seatNumber;
    @NotNull(message = "Theater::seatPerLine must not be null")
    private Integer seatPerLine;
    private List<String> publicInfos;
    @NotNull(message = "Theater::discounts must not be null")
    private Map<Level, Double> discounts;
    private String avatar;

    public Theater(String id, String password, String name, Integer regionId, String location, Integer seatNumber, Integer seatPerLine, Map<Level, Double> discounts) {
        this.id = id;
        this.condition = Condition.EXISTING;
        this.role = Role.THEATER;
        this.password = password;
        this.name = name;
        this.regionId = regionId;
        this.location = location;
        this.seatNumber = seatNumber;
        this.seatPerLine = seatPerLine;
        this.publicInfos = Collections.emptyList();
        this.discounts = discounts;
        this.avatar = DEFAULT_AVATAR;
    }

    public Theater(UserTheater userTheater) {
        this.id = userTheater.getId();
        this.condition = Condition.EXISTING;
        this.role = Role.THEATER;
        this.password = userTheater.getPassword();
        this.name = userTheater.getName();
        this.regionId = userTheater.getRegionId();
        this.location = userTheater.getLocation();
        this.seatNumber = userTheater.getSeatNumber();
        this.seatPerLine = userTheater.getSeatPerLine();
        this.publicInfos = Collections.emptyList();
        this.discounts = userTheater.getDiscounts();
        this.avatar = userTheater.getAvatar();
    }
}
