/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.List;

/**
 * 送货地址同步接口响应DTO.
 * 
 * @author fengwanjun
 */
public class GetAddressResponse {

    private String countryCode;
    
    private String country;
    
    private List<State> states;
    
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    /**
     * 送货地址-洲省.
     * 
     * @author fengwanjun
     */
    public static class State {
        private String state;

        private String stateCode;

        private List<City> cities;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public List<City> getCities() {
            return cities;
        }

        public void setCities(List<City> cities) {
            this.cities = cities;
        }

        /**
         * 送货地址-城市.
         * 
         * @author fengwanjun
         */
        public static class City {
            private String city;

            private String cityCode;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }
        }
    }
}