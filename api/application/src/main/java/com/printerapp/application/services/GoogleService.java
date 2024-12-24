package com.printerapp.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.printerapp.application.results.google.GoogleResult;

public interface GoogleService {
    GoogleResult outboundAuthentication(String googleCode) throws JsonMappingException, JsonProcessingException;
}
