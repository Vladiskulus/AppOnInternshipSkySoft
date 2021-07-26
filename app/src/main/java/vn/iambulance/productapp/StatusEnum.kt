package vn.iambulance.productapp

enum class StatusEnum(var status: String) {
    SUCCESS("SUCCESS"),
    EMAIL_WRONG("Incorrect Email input"),
    EMAIL_NOT_REGISTERED("eMail is not registered"),
    EMAIL_REGISTERED("This email registered"),
    PASSWORD_WRONG("Incorrect password input"),
    PASSWORD_DO_NOT_MATCH("Passwords do not match"),
    ACCOUNT_ERROR("Enter an account")
}