package com.mobiquity.packer.service;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.ValidationResult;
import java.util.Collection;
import java.util.Objects;

/**
 * Validates the {@link com.mobiquity.packer.model.PackingRequest}.
 */
public class PackingRequestValidator {
  private int maxWeight = 100;
  private int maxItemsCount = 15;
  private int maxItemWeight = 100;
  private int maxItemCost = 100;

  /**
   * Validate a provided collection of {@link PackingRequest}.
   * @param requests to check
   * @return an instance of {@link ValidationResult} which holds the validation status.
   */
  public ValidationResult validate(final Collection<PackingRequest> requests) {
    Objects.requireNonNull(requests, "Requests should be provided");

    for (PackingRequest request : requests) {
      if (request.getMaxWeight() > maxWeight) {
        return ValidationResult.error("Max weight exceeded");
      }

      if (request.getMaxWeight() < 0) {
        return ValidationResult.error("Weight should not be negative");
      }

      if (request.getItems().size() > maxItemsCount) {
        return ValidationResult.error("Max items count exceeded");
      }

      for (BackpackItem item : request.getItems()) {
        if (item.getWeight() > maxItemWeight) {
          return ValidationResult.error("Item has exceeding weight");
        }

        if (item.getWeight() < 0) {
          return ValidationResult.error("Weight should not be negative");
        }

        if (item.getCost().getNumber().intValueExact() > maxItemCost) {
          return ValidationResult.error("Item has exceeding cost");
        }

        if (item.getCost().getNumber().intValueExact() < 0) {
          return ValidationResult.error("Cost should not be negative");
        }
      }
    }

    return ValidationResult.success();
  }
}
