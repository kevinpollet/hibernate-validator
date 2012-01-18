/*
* JBoss, Home of Professional Open Source
* Copyright 2010, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package org.hibernate.validator.util;

/**
 * @author Gunnar Morling
 * @author Hardy Ferentschik
 * @author Kevin Pollet <kevin.pollet@serli.com> (C) 2012 SERLI
 */
public final class Contracts {

	private Contracts() {
	}

	public static void assertNotNull(Object o) {
		assertNotNull( o, "must not be null" );
	}

	public static void assertNotNull(Object o, String message) {
		if ( o == null ) {
			throw new IllegalArgumentException( message );
		}
	}

	public static void assertTrue(boolean condition, String message) {
		if ( !condition ) {
			throw new IllegalArgumentException( message );
		}
	}
}