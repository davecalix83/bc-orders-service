package com.cuscatlan.orders.domain.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an error response structure for API error messages.
 * This class is used to encapsulate error details sent in the response body.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /** The status of the error (e.g., "ERROR", "FAILURE"). */
    private String status;

    /** A descriptive message detailing the error encountered. */
    private String message;

    /** The timestamp when the error was processed. */
    private LocalDateTime processedAt;
}
