package com.benny.library.tiny;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Result {
    public int position;
    public List<Object> results = new ArrayList<>();

    public Result(int position, Object... results) {
        this.position = position;
        this.results.addAll(Arrays.asList(results));
    }
}
