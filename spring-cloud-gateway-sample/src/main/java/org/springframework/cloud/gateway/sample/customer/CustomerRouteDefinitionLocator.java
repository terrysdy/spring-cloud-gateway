package org.springframework.cloud.gateway.sample.customer;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.support.RouteMetadataUtils;
import org.springframework.stereotype.Component;

/**
 * Create on 2020/10/29 Company：共道网络科技
 *
 * <pre>
 * 自定义实现route加载
 * </pre>
 *
 * @author shendeyuan
 * @version 1.0
 */
@Component
public class CustomerRouteDefinitionLocator implements RouteDefinitionLocator {
	@lombok.SneakyThrows
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setId("customer_load_route");

		PredicateDefinition predicateDefinition = new PredicateDefinition("Path=/customer/{segment}");
		routeDefinition.setPredicates(Collections.singletonList(predicateDefinition));
		FilterDefinition filterDefinition = new FilterDefinition("AddRequestParameter=red, blue");
		FilterDefinition filterDefinition1 = new FilterDefinition("SetPath=/{segment}");
		routeDefinition.setFilters(Arrays.asList(filterDefinition, filterDefinition1));

		routeDefinition.setUri(new URI("http://httpbin.org:80"));

		Map<String, Object> metaData = new HashMap<>();
		// 超时时间
		metaData.put(RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR, 5000);
		routeDefinition.setMetadata(metaData);

		return Flux.just(routeDefinition);
	}
}
