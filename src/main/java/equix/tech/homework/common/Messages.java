package equix.tech.homework.common;

public class Messages {
    // common
    public static final String SUCCESS = "Success";
    public static final String FAILED = "Fail";

    // validation
    public static final String SYMBOL_REQUIRED = "Symbol is required.";
    public static final String SYMBOL_FORMAT_INVALID = "Symbol must be uppercase letters (max 10 characters).";

    public static final String PRICE_REQUIRED = "Price must not be null.";
    public static final String PRICE_MIN = "Price must be greater than 0.";

    public static final String QUANTITY_REQUIRED = "Quantity must not be null.";
    public static final String QUANTITY_MIN = "Quantity must be at least 1.";

    public static final String SIDE_REQUIRED = "Side must be provided.";

    // Business-level errors
    public static final String ORDER_NOT_FOUND = "Order not found with ID: %s";
    public static final String ORDER_INVALID_STATE = "Only PENDING orders can be cancelled.";

    // success
    public static final String SIMULATE_EXECUTION = "Simulate execution handled";
    public static final String ORDER_CREATE = "Order handling";
    public static final String ORDER_CANCELED = "Order canceled.";
}
