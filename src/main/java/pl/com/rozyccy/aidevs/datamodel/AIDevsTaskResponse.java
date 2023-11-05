package pl.com.rozyccy.aidevs.datamodel;

import java.util.List;

public record AIDevsTaskResponse(
    int code,
    String msg,
    String token,
    String cookie,
    List<String> input,
    List<String> blog,
    String answer,
    String question) {}
