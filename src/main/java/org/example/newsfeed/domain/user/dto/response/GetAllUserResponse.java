package org.example.newsfeed.domain.user.dto.response;

import org.example.newsfeed.domain.user.dto.UserDTO;

import java.util.List;

public class GetAllUserResponse {

    private final List<UserDTO> getUserDTOList;

    public GetAllUserResponse(List<UserDTO> getUserDTOList) {
        this.getUserDTOList = getUserDTOList;
    }

    public List<UserDTO> getUserDtoList() {
        return this.getUserDTOList;
    }

}
