/*
 *      Copyright (c) 2013-2016 Stuart Boston
 *
 *      This file is part of the OMDB API.
 *
 *      The OMDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      The OMDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with the OMDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.movieknight.movieknight.OmdbAPI.tools;
import com.movieknight.movieknight.OmdbAPI.OMDBException;
import com.movieknight.movieknight.OmdbAPI.emumerations.PlotType;
import com.movieknight.movieknight.OmdbAPI.emumerations.ResultType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 * Create a build object to build the request
 *
 * @author Stuart.Boston
 */
public final class OmdbBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbBuilder.class);
    private final OmdbParameters params = new OmdbParameters();
    // Default values (if required)
    private static final boolean DEFAULT_TOMATOES = Boolean.FALSE;

    /**
     * Construct the object
     *
     */
    public OmdbBuilder() {
        // Create an empty builder.
    }

    /**
     * Set the search term
     *
     * @param searchTerm The text to build for
     * @return
     * @throws com.omertron.omdbapi.OMDBException
     */
    public OmdbBuilder setSearchTerm(final String searchTerm) throws OMDBException {
        if (StringUtils.isBlank(searchTerm)) {
            throw new OMDBException(ApiExceptionType.UNKNOWN_CAUSE, "Must provide a search term!");
        }
        params.add(Param.SEARCH, searchTerm);
        return this;
    }

    /**
     * A valid IMDb ID
     *
     * @param imdbId The IMDB ID
     * @return
     * @throws OMDBException
     */
    public OmdbBuilder setImdbId(final String imdbId) throws OMDBException {
        if (StringUtils.isBlank(imdbId)) {
            throw new OMDBException(ApiExceptionType.UNKNOWN_CAUSE, "Must provide an IMDB ID!");
        }
        params.add(Param.IMDB, imdbId);
        return this;
    }

    /**
     * The title to search for
     *
     * @param title
     * @return
     * @throws OMDBException
     */
    public OmdbBuilder setTitle(final String title) throws OMDBException {
        if (StringUtils.isBlank(title)) {
            throw new OMDBException(ApiExceptionType.UNKNOWN_CAUSE, "Must provide a title!");
        }
        params.add(Param.TITLE, title);
        return this;
    }

    /**
     * Set the year
     *
     * @param year [OPTIONAL] the year to limit the build to
     * @return
     */
    public OmdbBuilder setYear(final int year) {
        if (year > 0) {
            params.add(Param.YEAR, year);
        }
        return this;
    }

    /**
     * The type of video to return: Movie, Series or Episode.
     *
     * @param resultType [OPTIONAL] The type to limit the build to
     * @return
     */
    public OmdbBuilder setResultType(final ResultType resultType) {
        if (!ResultType.isDefault(resultType)) {
            params.add(Param.RESULT, resultType);
        }
        return this;
    }

    /**
     * Limit the results to movies
     *
     * @return
     */
    public OmdbBuilder setTypeMovie() {
        if (!ResultType.isDefault(ResultType.MOVIE)) {
            params.add(Param.RESULT, ResultType.MOVIE);
        }
        return this;
    }

    /**
     * Limit the results to series
     *
     * @return
     */
    public OmdbBuilder setTypeSeries() {
        if (!ResultType.isDefault(ResultType.SERIES)) {
            params.add(Param.RESULT, ResultType.SERIES);
        }
        return this;
    }

    /**
     * Limit the results to episodes
     *
     * @return
     */
    public OmdbBuilder setTypeEpisode() {
        if (!ResultType.isDefault(ResultType.EPISODE)) {
            params.add(Param.RESULT, ResultType.EPISODE);
        }
        return this;
    }

    /**
     * Return short or full plot.
     *
     * @param plotType The plot type to get
     * @return
     */
    public OmdbBuilder setPlot(final PlotType plotType) {
        if (!PlotType.isDefault(plotType)) {
            params.add(Param.PLOT, plotType);
        }
        return this;
    }

    /**
     * Return the long plot
     *
     * @return
     */
    @Deprecated
    public OmdbBuilder setPlotLong() {
        setPlotFull();
        return this;
    }

    /**
     * Return the long plot
     *
     * @return
     */
    public OmdbBuilder setPlotFull() {
        if (!PlotType.isDefault(PlotType.FULL)) {
            params.add(Param.PLOT, PlotType.FULL);
        }
        return this;
    }

    /**
     * Return the short plot
     *
     * @return
     */
    public OmdbBuilder setPlotShort() {
        if (!PlotType.isDefault(PlotType.SHORT)) {
            params.add(Param.PLOT, PlotType.SHORT);
        }
        return this;
    }

    /**
     * Include or exclude Rotten Tomatoes ratings.
     *
     * @param tomatoes
     * @return
     */
    public OmdbBuilder setTomatoes(boolean tomatoes) {
        if (DEFAULT_TOMATOES != tomatoes) {
            params.add(Param.TOMATOES, tomatoes);
        }
        return this;
    }

    /**
     * Include Rotten Tomatoes ratings.
     *
     * @return
     */
    public OmdbBuilder setTomatoesOn() {
        return setTomatoes(true);
    }

    /**
     * Exclude Rotten Tomatoes ratings.
     *
     * @return
     */
    public OmdbBuilder setTomatoesOff() {
        return setTomatoes(false);
    }

    /**
     * API version
     *
     * @param version [OPTIONAL] The API version to use
     * @return
     */
    public OmdbBuilder setVersion(final int version) {
        params.add(Param.VERSION, version);
        return this;
    }
    
    /**
     * Set the ApiKey
     *
     * @param apiKey The apikey needed for the contract
     * @return
     * @throws com.omertron.omdbapi.OMDBException
     */
    public OmdbBuilder setApiKey(final String apiKey) throws OMDBException {
        if (StringUtils.isBlank(apiKey)) {
            throw new OMDBException(ApiExceptionType.AUTH_FAILURE, "Must provide an ApiKey");
        }
        params.add(Param.APIKEY, apiKey);
        return this;
    }    

    /**
     * Generate the parameters
     *
     * @return
     */
    public OmdbParameters build() {
        LOG.trace(toString());
        return params;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(params, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
