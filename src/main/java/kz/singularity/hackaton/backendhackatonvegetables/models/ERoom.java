package kz.singularity.hackaton.backendhackatonvegetables.models;

import java.util.Optional;

public enum ERoom {
    R404("Lecture room 1", false),
    R403("Singularity team office", null),
    R301("Kitchen", true),
    R302("Coworking", false),
    R303("Conference room", true),
    R304("Mac room", false),
    R203("Place of rest", true);

    private final String description;
    private final Boolean permission;

    ERoom(String description, Boolean permission) {
        this.description = description;
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPermission() {
        return permission;
    }
}
