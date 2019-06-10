package com.jxy.study.entity
import javax.validation.constraints.NotNull


class GJxyUser extends AbstractEntity  {
    @NotNull
    String userName
    @NotNull
    String userPass
    String userRole
}
