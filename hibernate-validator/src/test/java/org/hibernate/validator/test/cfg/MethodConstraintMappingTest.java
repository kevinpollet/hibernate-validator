/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.validator.test.cfg;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.cfg.defs.SizeDef;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.hibernate.validator.method.MethodValidator;
import org.hibernate.validator.test.util.ValidationInvocationHandler;

import static org.hibernate.validator.cfg.ConstraintDef.create;
import static org.hibernate.validator.test.util.TestUtil.assertCorrectConstraintViolationMessages;
import static org.hibernate.validator.test.util.TestUtil.assertCorrectPropertyPaths;
import static org.hibernate.validator.test.util.TestUtil.getMethodValidationProxy;
import static org.hibernate.validator.test.util.TestUtil.getMethodValidatorForMapping;
import static org.testng.Assert.fail;

/**
 * Tests the definition of method constraints with the programmatic API.
 *
 * @author Kevin Pollet - SERLI - (kevin.pollet@serli.com)
 */
public class MethodConstraintMappingTest {

	private GreetingService wrappedObject;

	@BeforeClass
	public void setUp() {
		wrappedObject = new GreetingServiceImpl();
	}

	@Test
	public void testCascadingMethodReturnDefinition() {
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet", User.class )
				.returnValue()
				.valid();

		GreetingService service = getValidatingProxy( wrappedObject, mapping );

		try {
			service.greet( new User( "foo" ) );
			fail( "Expected exception wasn't thrown." );
		}
		catch ( MethodConstraintViolationException e ) {
			assertCorrectConstraintViolationMessages( e, "may not be null" );
			assertCorrectPropertyPaths( e, "GreetingService#greet().message" );
		}
	}

	@Test
	public void testCascadingMethodParameterDefinition() {
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet", User.class )
				.parameter( 0 )
				.valid();

		GreetingService service = getValidatingProxy( wrappedObject, mapping );

		try {
			service.greet( new User( null ) );
			fail( "Expected exception wasn't thrown." );
		}
		catch ( MethodConstraintViolationException e ) {
			assertCorrectConstraintViolationMessages( e, "may not be null" );
			assertCorrectPropertyPaths( e, "GreetingService#greet(arg0).name" );
		}
	}

	@Test(
			expectedExceptions = ValidationException.class,
			expectedExceptionsMessageRegExp = "The .* doesn't have a method 'greet' with parameter types \\[\\]"
	)
	public void testCascadingDefinitionOnMissingMethod() {
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet" )
				.returnValue()
				.valid();

		getMethodValidatorForMapping( mapping );
	}

	@Test(
			expectedExceptions = ValidationException.class,
			expectedExceptionsMessageRegExp = "A valid parameter index has to be specified for method 'greet'"
	)
	public void testCascadingDefinitionOnInvalidMethodParameter() {
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet", User.class )
				.parameter( 1 )
				.valid();

		getMethodValidatorForMapping( mapping );
	}

	@Test(
			expectedExceptions = ConstraintDeclarationException.class,
			expectedExceptionsMessageRegExp = ".* there are parameter constraints defined at all of the following overridden methods: .*"
	)
	public void testCascadingMethodParameterRedefinedInHierarchy() {
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet", User.class )
				.parameter( 0 )
				.valid()
				.type( GreetingServiceImpl.class )
				.method( "greet", User.class )
				.parameter( 0 )
				.valid();

		GreetingService service = getValidatingProxy( wrappedObject, mapping );

		service.greet( new User( null ) );
	}

	@Test(
			expectedExceptions = ConstraintDeclarationException.class,
			expectedExceptionsMessageRegExp = ".* The following method itself has no parameter constraints but it is not defined on a sub-type of .*"
	)
	public void testCascadingMethodParameterDefinedOnlyOnSubType() {
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingServiceImpl.class )
				.method( "greet", User.class )
				.parameter( 0 )
				.valid();

		GreetingService service = getValidatingProxy( wrappedObject, mapping );

		service.greet( new User( null ) );
	}

	@Test
	public void testParameterConstraint() {

		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet", User.class )
				.parameter( 0 )
				.constraint( create( NotNullDef.class ) );

		try {
			GreetingService service = getValidatingProxy( wrappedObject, mapping );
			service.greet( (User) null );

			fail( "Expected exception wasn't thrown." );
		}
		catch ( MethodConstraintViolationException e ) {

			assertCorrectConstraintViolationMessages( e, "may not be null" );
			assertCorrectPropertyPaths( e, "GreetingService#greet(arg0)" );
		}
	}

	@Test
	public void testMultipleParameterConstraintsAtSameParameter() {

		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet", String.class )
				.parameter( 0 )
				.constraint( create( SizeDef.class ).min( 1 ).max( 10 ) )
				.constraint( create( SizeDef.class ).min( 2 ).max( 10 ) );

		try {
			GreetingService service = getValidatingProxy( wrappedObject, mapping );
			service.greet( "" );

			fail( "Expected exception wasn't thrown." );
		}
		catch ( MethodConstraintViolationException e ) {

			assertCorrectConstraintViolationMessages(
					e, "size must be between 1 and 10", "size must be between 2 and 10"
			);
			assertCorrectPropertyPaths( e, "GreetingService#greet(arg0)", "GreetingService#greet(arg0)" );
		}
	}

	@Test
	public void testMultipleParameterConstraintsAtDifferentParameters() {

		ConstraintMapping mapping = new ConstraintMapping();
		mapping.type( GreetingService.class )
				.method( "greet", String.class, String.class )
				.parameter( 0 )
				.constraint( create( SizeDef.class ).min( 1 ).max( 10 ) )
				.parameter( 1 )
				.constraint( create( SizeDef.class ).min( 1 ).max( 10 ) );

		try {
			GreetingService service = getValidatingProxy( wrappedObject, mapping );
			service.greet( "", "" );

			fail( "Expected exception wasn't thrown." );
		}
		catch ( MethodConstraintViolationException e ) {

			assertCorrectConstraintViolationMessages(
					e, "size must be between 1 and 10", "size must be between 1 and 10"
			);
			assertCorrectPropertyPaths( e, "GreetingService#greet(arg0)", "GreetingService#greet(arg1)" );
		}
	}

	private <T> T getValidatingProxy(Object implementor, ConstraintMapping mapping) {

		MethodValidator methodValidator = getMethodValidatorForMapping( mapping );
		ValidationInvocationHandler handler = new ValidationInvocationHandler( wrappedObject, methodValidator );

		@SuppressWarnings("unchecked")
		T service = (T) getMethodValidationProxy( handler );

		return service;
	}

	public class User {

		@SuppressWarnings("unused")
		@NotNull
		private String name;

		public User(String name) {
			this.name = name;
		}
	}

	public class Message {

		@SuppressWarnings("unused")
		@NotNull
		private String message;

		public Message(String message) {
			this.message = message;
		}
	}

	public interface GreetingService {
		Message greet(User user);

		Message greet(String string);

		Message greet(String string1, String string2);
	}

	public class GreetingServiceImpl implements GreetingService {
		public Message greet(User user) {
			return new Message( null );
		}

		public Message greet(String string) {
			return null;
		}

		public Message greet(String string1, String string2) {
			return null;
		}
	}
}