package test.runtime.tests.service;

import org.springframework.test.context.ContextConfiguration;

import test.runtime.AbstractRuntimeTest;

@ContextConfiguration(locations = { "file:src/test/runtime/tests/service/configuration/test-context.xml", "file:src/test/runtime/tests/service/configuration/test-servlet.xml" })
public abstract class AbstractServiceTest extends AbstractRuntimeTest {}
