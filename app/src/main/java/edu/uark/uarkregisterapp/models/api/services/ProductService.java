package edu.uark.uarkregisterapp.models.api.services;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.ProductListing;
import edu.uark.uarkregisterapp.models.api.enums.ApiLevel;
import edu.uark.uarkregisterapp.models.api.enums.ProductApiMethod;
import edu.uark.uarkregisterapp.models.api.enums.ProductApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class ProductService extends BaseRemoteService {
	public Product getProduct(UUID productId) {
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { ProductApiMethod.PRODUCT, ApiLevel.ONE }), productId
		);

		if (rawJsonObject != null) {
			return (new Product()).loadFromJson(rawJsonObject);
		} else {
			return new Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR);
		}
	}

	public Product getProductByLookupCode(String productLookupCode) {
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { ProductApiMethod.PRODUCT, ApiLevel.ONE, ProductApiMethod.BY_LOOKUP_CODE }), productLookupCode
		);

		if (rawJsonObject != null) {
			return (new Product()).loadFromJson(rawJsonObject);
		} else {
			return new Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR);
		}
	}

	public List<Product> getProducts() {
		List<Product> activities;
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { ProductApiMethod.PRODUCT, ApiLevel.ONE, ProductApiMethod.PRODUCTS })
		);

		if (rawJsonObject != null) {
			activities = (new ProductListing()).loadFromJson(rawJsonObject).getProducts();
		} else {
			activities = new ArrayList<>(0);
		}

		return activities;
	}

	public Product putProduct(Product product) {
		JSONObject rawJsonObject = this.putSingle(
			(new PathElementInterface[]{ ProductApiMethod.PRODUCT, ApiLevel.ONE }), product.convertToJson()
		);

		if (rawJsonObject != null) {
			return (new Product()).loadFromJson(rawJsonObject);
		} else {
			return new Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR);
		}
	}
}
