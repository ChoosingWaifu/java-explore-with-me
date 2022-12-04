package explorewithme.dto;

import explorewithme.model.Hit;

public class Mapper {

    public static Hit toHit(NewHit newHit) {
        return new Hit(
                null,
                newHit.getApp(),
                newHit.getUri(),
                newHit.getIp(),
                null
        );
    }

    public static HitDto toHitDto(Hit hit) {
        return new HitDto(
                hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp()
        );
    }
}
