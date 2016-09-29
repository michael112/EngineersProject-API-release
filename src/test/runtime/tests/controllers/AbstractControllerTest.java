package test.runtime.tests.controllers;

import org.springframework.test.context.ContextConfiguration;

import test.runtime.AbstractRuntimeTest;

@ContextConfiguration(locations = { "file:src/test/runtime/tests/controllers/configuration/test-context.xml", "file:src/test/runtime/tests/controllers/configuration/test-servlet.xml" })
public abstract class AbstractControllerTest extends AbstractRuntimeTest {}
