/*
 * Copyright 2022 MkSoo01
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.pokedex.data;

import java.util.Objects;

public class ResponseStatus {
    private int responseCode;
    private final boolean isSuccess;
    private String responseCodeMessage;
    private String errorMessage;

    public ResponseStatus(int responseCode, boolean isSuccess) {
        this.responseCode = responseCode;
        this.isSuccess = isSuccess;
        this.responseCodeMessage = getResponseCodeMessage(responseCode);
    }

    public ResponseStatus(int responseCode, boolean isSuccess, String errorMessage) {
        this.responseCode = responseCode;
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
        this.responseCodeMessage = getResponseCodeMessage(responseCode);
    }

    public ResponseStatus(String errorMessage) {
        this.isSuccess = false;
        this.errorMessage = errorMessage;
    }

    public static String getResponseCodeMessage(int responseCode){
        switch(responseCode){
            case 200:
                return "Success";
            case 400:
                return "Bad Request";
            case 401:
                return "Unauthorized";
            case 403:
                return "Forbidden";
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            case 503:
                return "Service Unavailable";
            default:
                return "";
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getResponseCodeMessage() {
        return responseCodeMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseStatus that = (ResponseStatus) o;
        return responseCode == that.responseCode && isSuccess == that.isSuccess;
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, isSuccess);
    }
}
