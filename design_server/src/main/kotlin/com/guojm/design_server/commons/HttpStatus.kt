package com.guojm.design_server.commons

/**
 * Enumeration of HTTP status codes.
 *
 *
 * The HTTP status code series can be retrieved via [.series].
 *
 * @author Arjen Poutsma
 * @author Sebastien Deleuze
 * @author Brian Clozel
 * @since 3.0
 * @see HttpStatus.Series
 *
 * @see [HTTP Status Code Registry](https://www.iana.org/assignments/http-status-codes)
 *
 * @see [List of HTTP status codes - Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)
 */
enum class HttpStatus(
    private val value: Int,
    /**
     * Return the reason phrase of this status code.
     */
    val reasonPhrase: String
) {
    // 1xx Informational
    /**
     * `100 Continue`.
     * @see [HTTP/1.1: Semantics and Content, section 6.2.1](https://tools.ietf.org/html/rfc7231.section-6.2.1)
     */
    CONTINUE(100, "Continue"),

    /**
     * `101 Switching Protocols`.
     * @see [HTTP/1.1: Semantics and Content, section 6.2.2](https://tools.ietf.org/html/rfc7231.section-6.2.2)
     */
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    /**
     * `102 Processing`.
     * @see [WebDAV](https://tools.ietf.org/html/rfc2518.section-10.1)
     */
    PROCESSING(102, "Processing"),

    /**
     * `103 Checkpoint`.
     * @see [A proposal for supporting
     * resumable POST/PUT HTTP requests in HTTP/1.0](https://code.google.com/p/gears/wiki/ResumableHttpRequestsProposal)
     */
    CHECKPOINT(103, "Checkpoint"),  // 2xx Success

    /**
     * `200 OK`.
     * @see [HTTP/1.1: Semantics and Content, section 6.3.1](https://tools.ietf.org/html/rfc7231.section-6.3.1)
     */
    OK(200, "OK"),

    /**
     * `201 Created`.
     * @see [HTTP/1.1: Semantics and Content, section 6.3.2](https://tools.ietf.org/html/rfc7231.section-6.3.2)
     */
    CREATED(201, "Created"),

    /**
     * `202 Accepted`.
     * @see [HTTP/1.1: Semantics and Content, section 6.3.3](https://tools.ietf.org/html/rfc7231.section-6.3.3)
     */
    ACCEPTED(202, "Accepted"),

    /**
     * `203 Non-Authoritative Information`.
     * @see [HTTP/1.1: Semantics and Content, section 6.3.4](https://tools.ietf.org/html/rfc7231.section-6.3.4)
     */
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),

    /**
     * `204 No Content`.
     * @see [HTTP/1.1: Semantics and Content, section 6.3.5](https://tools.ietf.org/html/rfc7231.section-6.3.5)
     */
    NO_CONTENT(204, "No Content"),

    /**
     * `205 Reset Content`.
     * @see [HTTP/1.1: Semantics and Content, section 6.3.6](https://tools.ietf.org/html/rfc7231.section-6.3.6)
     */
    RESET_CONTENT(205, "Reset Content"),

    /**
     * `206 Partial Content`.
     * @see [HTTP/1.1: Range Requests, section 4.1](https://tools.ietf.org/html/rfc7233.section-4.1)
     */
    PARTIAL_CONTENT(206, "Partial Content"),

    /**
     * `207 Multi-Status`.
     * @see [WebDAV](https://tools.ietf.org/html/rfc4918.section-13)
     */
    MULTI_STATUS(207, "Multi-Status"),

    /**
     * `208 Already Reported`.
     * @see [WebDAV Binding Extensions](https://tools.ietf.org/html/rfc5842.section-7.1)
     */
    ALREADY_REPORTED(208, "Already Reported"),

    /**
     * `226 IM Used`.
     * @see [Delta encoding in HTTP](https://tools.ietf.org/html/rfc3229.section-10.4.1)
     */
    IM_USED(226, "IM Used"),  // 3xx Redirection

    /**
     * `300 Multiple Choices`.
     * @see [HTTP/1.1: Semantics and Content, section 6.4.1](https://tools.ietf.org/html/rfc7231.section-6.4.1)
     */
    MULTIPLE_CHOICES(300, "Multiple Choices"),

    /**
     * `301 Moved Permanently`.
     * @see [HTTP/1.1: Semantics and Content, section 6.4.2](https://tools.ietf.org/html/rfc7231.section-6.4.2)
     */
    MOVED_PERMANENTLY(301, "Moved Permanently"),

    /**
     * `302 Found`.
     * @see [HTTP/1.1: Semantics and Content, section 6.4.3](https://tools.ietf.org/html/rfc7231.section-6.4.3)
     */
    FOUND(302, "Found"),

    /**
     * `302 Moved Temporarily`.
     * @see [HTTP/1.0, section 9.3](https://tools.ietf.org/html/rfc1945.section-9.3)
     *
     */
    MOVED_TEMPORARILY(302, "Moved Temporarily"),

    /**
     * `303 See Other`.
     * @see [HTTP/1.1: Semantics and Content, section 6.4.4](https://tools.ietf.org/html/rfc7231.section-6.4.4)
     */
    SEE_OTHER(303, "See Other"),

    /**
     * `304 Not Modified`.
     * @see [HTTP/1.1: Conditional Requests, section 4.1](https://tools.ietf.org/html/rfc7232.section-4.1)
     */
    NOT_MODIFIED(304, "Not Modified"),

    /**
     * `305 Use Proxy`.
     * @see [HTTP/1.1: Semantics and Content, section 6.4.5](https://tools.ietf.org/html/rfc7231.section-6.4.5)
     *
     */
    USE_PROXY(305, "Use Proxy"),

    /**
     * `307 Temporary Redirect`.
     * @see [HTTP/1.1: Semantics and Content, section 6.4.7](https://tools.ietf.org/html/rfc7231.section-6.4.7)
     */
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),

    /**
     * `308 Permanent Redirect`.
     * @see [RFC 7238](https://tools.ietf.org/html/rfc7238)
     */
    PERMANENT_REDIRECT(308, "Permanent Redirect"),  // --- 4xx Client Error ---

    /**
     * `400 Bad Request`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.1](https://tools.ietf.org/html/rfc7231.section-6.5.1)
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * `401 Unauthorized`.
     * @see [HTTP/1.1: Authentication, section 3.1](https://tools.ietf.org/html/rfc7235.section-3.1)
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * `402 Payment Required`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.2](https://tools.ietf.org/html/rfc7231.section-6.5.2)
     */
    PAYMENT_REQUIRED(402, "Payment Required"),

    /**
     * `403 Forbidden`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.3](https://tools.ietf.org/html/rfc7231.section-6.5.3)
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * `404 Not Found`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.4](https://tools.ietf.org/html/rfc7231.section-6.5.4)
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * `405 Method Not Allowed`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.5](https://tools.ietf.org/html/rfc7231.section-6.5.5)
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     * `406 Not Acceptable`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.6](https://tools.ietf.org/html/rfc7231.section-6.5.6)
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),

    /**
     * `407 Proxy Authentication Required`.
     * @see [HTTP/1.1: Authentication, section 3.2](https://tools.ietf.org/html/rfc7235.section-3.2)
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),

    /**
     * `408 Request Timeout`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.7](https://tools.ietf.org/html/rfc7231.section-6.5.7)
     */
    REQUEST_TIMEOUT(408, "Request Timeout"),

    /**
     * `409 Conflict`.
     * @see [HTTP/1.1: Semantics and Content, section 6.5.8](https://tools.ietf.org/html/rfc7231.section-6.5.8)
     */
    CONFLICT(409, "Conflict"),

    /**
     * `410 Gone`.
     * @see [
     * HTTP/1.1: Semantics and Content, section 6.5.9](https://tools.ietf.org/html/rfc7231.section-6.5.9)
     */
    GONE(410, "Gone"),

    /**
     * `411 Length Required`.
     * @see [
     * HTTP/1.1: Semantics and Content, section 6.5.10](https://tools.ietf.org/html/rfc7231.section-6.5.10)
     */
    LENGTH_REQUIRED(411, "Length Required"),

    /**
     * `412 Precondition failed`.
     * @see [
     * HTTP/1.1: Conditional Requests, section 4.2](https://tools.ietf.org/html/rfc7232.section-4.2)
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),

    /**
     * `413 Payload Too Large`.
     * @since 4.1
     * @see [
     * HTTP/1.1: Semantics and Content, section 6.5.11](https://tools.ietf.org/html/rfc7231.section-6.5.11)
     */
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),

    /**
     * `413 Request Entity Too Large`.
     * @see [HTTP/1.1, section 10.4.14](https://tools.ietf.org/html/rfc2616.section-10.4.14)
     *
     */
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),

    /**
     * `414 URI Too Long`.
     * @since 4.1
     * @see [
     * HTTP/1.1: Semantics and Content, section 6.5.12](https://tools.ietf.org/html/rfc7231.section-6.5.12)
     */
    URI_TOO_LONG(414, "URI Too Long"),

    /**
     * `414 Request-URI Too Long`.
     * @see [HTTP/1.1, section 10.4.15](https://tools.ietf.org/html/rfc2616.section-10.4.15)
     *
     */
    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),

    /**
     * `415 Unsupported Media Type`.
     * @see [
     * HTTP/1.1: Semantics and Content, section 6.5.13](https://tools.ietf.org/html/rfc7231.section-6.5.13)
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /**
     * `416 Requested Range Not Satisfiable`.
     * @see [HTTP/1.1: Range Requests, section 4.4](https://tools.ietf.org/html/rfc7233.section-4.4)
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),

    /**
     * `417 Expectation Failed`.
     * @see [
     * HTTP/1.1: Semantics and Content, section 6.5.14](https://tools.ietf.org/html/rfc7231.section-6.5.14)
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),

    /**
     * `418 I'm a teapot`.
     * @see [HTCPCP/1.0](https://tools.ietf.org/html/rfc2324.section-2.3.2)
     */
    I_AM_A_TEAPOT(418, "I'm a teapot"),
    INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
    METHOD_FAILURE(420, "Method Failure"),
    DESTINATION_LOCKED(421, "Destination Locked"),

    /**
     * `422 Unprocessable Entity`.
     * @see [WebDAV](https://tools.ietf.org/html/rfc4918.section-11.2)
     */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

    /**
     * `423 Locked`.
     * @see [WebDAV](https://tools.ietf.org/html/rfc4918.section-11.3)
     */
    LOCKED(423, "Locked"),

    /**
     * `424 Failed Dependency`.
     * @see [WebDAV](https://tools.ietf.org/html/rfc4918.section-11.4)
     */
    FAILED_DEPENDENCY(424, "Failed Dependency"),

    /**
     * `425 Too Early`.
     * @since 5.2
     * @see [RFC 8470](https://tools.ietf.org/html/rfc8470)
     */
    TOO_EARLY(425, "Too Early"),

    /**
     * `426 Upgrade Required`.
     * @see [Upgrading to TLS Within HTTP/1.1](https://tools.ietf.org/html/rfc2817.section-6)
     */
    UPGRADE_REQUIRED(426, "Upgrade Required"),

    /**
     * `428 Precondition Required`.
     * @see [Additional HTTP Status Codes](https://tools.ietf.org/html/rfc6585.section-3)
     */
    PRECONDITION_REQUIRED(428, "Precondition Required"),

    /**
     * `429 Too Many Requests`.
     * @see [Additional HTTP Status Codes](https://tools.ietf.org/html/rfc6585.section-4)
     */
    TOO_MANY_REQUESTS(429, "Too Many Requests"),

    /**
     * `431 Request Header Fields Too Large`.
     * @see [Additional HTTP Status Codes](https://tools.ietf.org/html/rfc6585.section-5)
     */
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),

    /**
     * `451 Unavailable For Legal Reasons`.
     * @see [
     * An HTTP Status Code to Report Legal Obstacles](https://tools.ietf.org/html/draft-ietf-httpbis-legally-restricted-status-04)
     *
     * @since 4.3
     */
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),  // --- 5xx Server Error ---

    /**
     * `500 Internal Server Error`.
     * @see [HTTP/1.1: Semantics and Content, section 6.6.1](https://tools.ietf.org/html/rfc7231.section-6.6.1)
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     * `501 Not Implemented`.
     * @see [HTTP/1.1: Semantics and Content, section 6.6.2](https://tools.ietf.org/html/rfc7231.section-6.6.2)
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),

    /**
     * `502 Bad Gateway`.
     * @see [HTTP/1.1: Semantics and Content, section 6.6.3](https://tools.ietf.org/html/rfc7231.section-6.6.3)
     */
    BAD_GATEWAY(502, "Bad Gateway"),

    /**
     * `503 Service Unavailable`.
     * @see [HTTP/1.1: Semantics and Content, section 6.6.4](https://tools.ietf.org/html/rfc7231.section-6.6.4)
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    /**
     * `504 Gateway Timeout`.
     * @see [HTTP/1.1: Semantics and Content, section 6.6.5](https://tools.ietf.org/html/rfc7231.section-6.6.5)
     */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),

    /**
     * `505 HTTP Version Not Supported`.
     * @see [HTTP/1.1: Semantics and Content, section 6.6.6](https://tools.ietf.org/html/rfc7231.section-6.6.6)
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),

    /**
     * `506 Variant Also Negotiates`
     * @see [Transparent Content Negotiation](https://tools.ietf.org/html/rfc2295.section-8.1)
     */
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),

    /**
     * `507 Insufficient Storage`
     * @see [WebDAV](https://tools.ietf.org/html/rfc4918.section-11.5)
     */
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),

    /**
     * `508 Loop Detected`
     * @see [WebDAV Binding Extensions](https://tools.ietf.org/html/rfc5842.section-7.2)
     */
    LOOP_DETECTED(508, "Loop Detected"),

    /**
     * `509 Bandwidth Limit Exceeded`
     */
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),

    /**
     * `510 Not Extended`
     * @see [HTTP Extension Framework](https://tools.ietf.org/html/rfc2774.section-7)
     */
    NOT_EXTENDED(510, "Not Extended"),

    /**
     * `511 Network Authentication Required`.
     * @see [Additional HTTP Status Codes](https://tools.ietf.org/html/rfc6585.section-6)
     */
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

    /**
     * Return the integer value of this status code.
     */
    fun value(): Int {
        return value
    }

    /**
     * Return the HTTP status series of this status code.
     * @see HttpStatus.Series
     */
    fun series(): Series {
        return Series.valueOf(this)
    }

    /**
     * Whether this status code is in the HTTP series
     * [org.springframework.http.HttpStatus.Series.INFORMATIONAL].
     * This is a shortcut for checking the value of [.series].
     * @since 4.0
     * @see .series
     */
    fun is1xxInformational(): Boolean {
        return series() == Series.INFORMATIONAL
    }

    /**
     * Whether this status code is in the HTTP series
     * [org.springframework.http.HttpStatus.Series.SUCCESSFUL].
     * This is a shortcut for checking the value of [.series].
     * @since 4.0
     * @see .series
     */
    fun is2xxSuccessful(): Boolean {
        return series() == Series.SUCCESSFUL
    }

    /**
     * Whether this status code is in the HTTP series
     * [org.springframework.http.HttpStatus.Series.REDIRECTION].
     * This is a shortcut for checking the value of [.series].
     * @since 4.0
     * @see .series
     */
    fun is3xxRedirection(): Boolean {
        return series() == Series.REDIRECTION
    }

    /**
     * Whether this status code is in the HTTP series
     * [org.springframework.http.HttpStatus.Series.CLIENT_ERROR].
     * This is a shortcut for checking the value of [.series].
     * @since 4.0
     * @see .series
     */
    fun is4xxClientError(): Boolean {
        return series() == Series.CLIENT_ERROR
    }

    /**
     * Whether this status code is in the HTTP series
     * [org.springframework.http.HttpStatus.Series.SERVER_ERROR].
     * This is a shortcut for checking the value of [.series].
     * @since 4.0
     * @see .series
     */
    fun is5xxServerError(): Boolean {
        return series() == Series.SERVER_ERROR
    }

    /**
     * Whether this status code is in the HTTP series
     * [org.springframework.http.HttpStatus.Series.CLIENT_ERROR] or
     * [org.springframework.http.HttpStatus.Series.SERVER_ERROR].
     * This is a shortcut for checking the value of [.series].
     * @since 5.0
     * @see .is4xxClientError
     * @see .is5xxServerError
     */
    val isError: Boolean
        get() = is4xxClientError() || is5xxServerError()

    /**
     * Return a string representation of this status code.
     */
    override fun toString(): String {
        return value.toString() + " " + name
    }

    /**
     * Enumeration of HTTP status series.
     *
     * Retrievable via [HttpStatus.series].
     */
    enum class Series(private val value: Int) {
        INFORMATIONAL(1), SUCCESSFUL(2), REDIRECTION(3), CLIENT_ERROR(4), SERVER_ERROR(5);

        /**
         * Return the integer value of this status series. Ranges from 1 to 5.
         */
        fun value(): Int {
            return value
        }

        companion object {
            /**
             * Return the enum constant of this type with the corresponding series.
             * @param status a standard HTTP status enum value
             * @return the enum constant of this type with the corresponding series
             * @throws IllegalArgumentException if this enum has no corresponding constant
             */
            fun valueOf(status: HttpStatus): Series {
                return valueOf(status.value)
            }

            /**
             * Return the enum constant of this type with the corresponding series.
             * @param statusCode the HTTP status code (potentially non-standard)
             * @return the enum constant of this type with the corresponding series
             * @throws IllegalArgumentException if this enum has no corresponding constant
             */
            fun valueOf(statusCode: Int): Series {
                return resolve(statusCode) ?: throw IllegalArgumentException("No matching constant for [$statusCode]")
            }

            /**
             * Resolve the given status code to an `HttpStatus.Series`, if possible.
             * @param statusCode the HTTP status code (potentially non-standard)
             * @return the corresponding `Series`, or `null` if not found
             * @since 5.1.3
             */
            fun resolve(statusCode: Int): Series? {
                val seriesCode = statusCode / 100
                for (series in values()) {
                    if (series.value == seriesCode) {
                        return series
                    }
                }
                return null
            }
        }

    }

    companion object {
        /**
         * Return the enum constant of this type with the specified numeric value.
         * @param statusCode the numeric value of the enum to be returned
         * @return the enum constant with the specified numeric value
         * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
         */
        fun valueOf(statusCode: Int): HttpStatus {
            return resolve(statusCode) ?: throw IllegalArgumentException("No matching constant for [$statusCode]")
        }

        /**
         * Resolve the given status code to an `HttpStatus`, if possible.
         * @param statusCode the HTTP status code (potentially non-standard)
         * @return the corresponding `HttpStatus`, or `null` if not found
         * @since 5.0
         */
        fun resolve(statusCode: Int): HttpStatus? {
            for (status in values()) {
                if (status.value == statusCode) {
                    return status
                }
            }
            return null
        }
    }

}