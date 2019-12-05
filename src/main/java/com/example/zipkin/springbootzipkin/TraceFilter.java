package com.example.zipkin.springbootzipkin;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import brave.Span;
import brave.Tracer;

/**
 * Servlet filter which Injects tracing headers into the HTTP response.
 * 
 * @author p.siva090
 *
 */
@Component
class TraceFilter extends GenericFilterBean {

	private final Tracer tracer;

	TraceFilter(Tracer tracer) {
		this.tracer = tracer;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Span currentSpan = this.tracer.currentSpan();
		if (currentSpan == null) {
			chain.doFilter(request, response);
			return;
		}
		// for readability we're returning trace id in a hex form
		((HttpServletResponse) response).addHeader("microservice1-traceid", currentSpan.context().traceIdString());
		// we can also add some custom tags
		// currentSpan.tag("custom", "tag");

		chain.doFilter(request, response);
	}

}
