package com.mobiquity.packer.model;

public class ValidationResult {
  private final boolean valid;
  private final String validationMessage;

  private ValidationResult(boolean valid, String validationMessage) {
    this.valid = valid;
    this.validationMessage = validationMessage;
  }

  public static ValidationResult success() {
    return new ValidationResult(true, "OK");
  }

  public static ValidationResult error(final String message) {
    return new ValidationResult(false, message);
  }

  public boolean isValid() {
    return valid;
  }

  public String getValidationMessage() {
    return validationMessage;
  }
}
