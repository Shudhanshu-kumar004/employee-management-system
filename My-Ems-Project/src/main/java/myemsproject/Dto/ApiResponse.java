package myemsproject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private String message;
    public int status;
    public T data;

}
