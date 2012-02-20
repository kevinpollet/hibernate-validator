/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package org.hibernate.validator.util.logging;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import javax.script.ScriptException;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.GroupDefinitionException;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
import javax.xml.bind.JAXBException;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Cause;
import org.jboss.logging.LogMessage;
import org.jboss.logging.Message;
import org.jboss.logging.MessageLogger;

import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.WARN;

/**
 * The logger interface for JBoss Logging.
 *
 * @author Kevin Pollet <kevin.pollet@serli.com> (C) 2012 SERLI
 */
@MessageLogger(projectCode = "HV")
public interface Log extends BasicLogger {

	@LogMessage(level = INFO)
	@Message(id = 1, value = "Hibernate Validator %s")
	void version(String version);

	@LogMessage(level = INFO)
	@Message(id = 2, value = "Ignoring XML configuration.")
	void ignoringXmlConfiguration();

	@LogMessage(level = INFO)
	@Message(id = 3, value = "Using %s as constraint factory.")
	void usingConstraintFactory(String constraintFactoryClassName);

	@LogMessage(level = INFO)
	@Message(id = 4, value = "Using %s as message interpolator.")
	void usingMessageInterpolator(String messageInterpolatorClassName);

	@LogMessage(level = INFO)
	@Message(id = 5, value = "Using %s as traversable resolver.")
	void usingTraversableResolver(String traversableResolverClassName);

	@LogMessage(level = INFO)
	@Message(id = 6, value = "Using %s as validation provider.")
	void usingValidationProvider(String validationProviderClassName);

	@LogMessage(level = INFO)
	@Message(id = 7, value = "%s found. Parsing XML based configuration.")
	void parsingXMLFile(String fileName);

	@LogMessage(level = WARN)
	@Message(id = 8, value = "Unable to close input stream.")
	void unableToCloseInputStream();

	@LogMessage(level = WARN)
	@Message(id = 9, value = "Unable to load provider class %s.")
	void unableToLoadProviderClass(String providerName);

	@LogMessage(level = WARN)
	@Message(id = 10, value = "Unable to close input stream for %s.")
	void unableToCloseXMLFileInputStream(String fileName);

	@LogMessage(level = WARN)
	@Message(id = 11, value = "Unable to create schema for %1$s: %2$s")
	void unableToCreateSchema(String fileName, String message);

	@Message(id = 12, value = "Unable to create annotation for configured constraint: %s.")
	ValidationException unableToCreateAnnotationForConfiguredConstraint(String message, @Cause RuntimeException e);

	@Message(id = 13, value = "The class %1$s does not have a property '%2$s' with access %3$s.")
	ValidationException unableToFindPropertyWithAccess(Class<?> beanClass, String property, ElementType elementType);

	@Message(id = 14, value = "Type %1$s doesn't have a method %2$s(%3$s).")
	IllegalArgumentException unableToFindMethod(Class<?> beanClass, String name, String parametersType);

	@Message(id = 15, value = "A valid parameter index has to be specified for method '%s'")
	IllegalArgumentException invalidMethodParameterIndex(String methodName);

	@Message(id = 16, value = "%s does not represent a valid BigDecimal format.")
	IllegalArgumentException invalidBigDecimalFormat(String value, @Cause NumberFormatException e);

	@Message(id = 17, value = "The length of the integer part cannot be negative.")
	IllegalArgumentException invalidLengthForIntegerPart();

	@Message(id = 18, value = "The length of the fraction part cannot be negative.")
	IllegalArgumentException invalidLengthForFractionPart();

	@Message(id = 19, value = "The min parameter cannot be negative.")
	IllegalArgumentException minCannotBeNegative();

	@Message(id = 20, value = "The max parameter cannot be negative.")
	IllegalArgumentException maxCannotBeNegative();

	@Message(id = 21, value = "The length cannot be negative.")
	IllegalArgumentException lengthCannotBeNegative();

	@Message(id = 22, value = "Invalid regular expression.")
	IllegalArgumentException invalidRegularExpression(@Cause PatternSyntaxException e);

	@Message(id = 23, value = "Error during execution of script \"%s\" occurred.")
	ConstraintDeclarationException errorDuringScriptExecution(String script, @Cause ScriptException e);

	@Message(id = 24, value = "Script \"%s\" returned null, but must return either true or false.")
	ConstraintDeclarationException scriptMustReturnTrueOrFalse(String script);

	@Message(id = 25, value = "Script \"%1$s\" returned %2$s (of type %3$s), but must return either true or false.")
	ConstraintDeclarationException scriptMustReturnTrueOrFalse(String script, Object executionResult, String type);

	@Message(id = 26, value = "Assertion error: inconsistent ConfigurationImpl construction.")
	ValidationException inconsistentConfiguration();

	@Message(id = 27, value = "Unable to find provider: %s.")
	ValidationException unableToFindProvider(Class<?> providerClass);

	@Message(id = 28, value = "Unexpected exception during isValid call.")
	ValidationException exceptionDuringIsValidCall(@Cause RuntimeException e);

	@Message(id = 29, value = "Constraint factory returned null when trying to create instance of %s.")
	ValidationException constraintFactoryMustNotReturnNull(String validatorClassName);

	@Message(id = 30, value = "No validator could be found for type: %s.")
	UnexpectedTypeException noValidatorFoundForType(String className);

	@Message(id = 31,
			value = "There are multiple validator classes which could validate the type %1$s. The validator classes are: %2$s.")
	UnexpectedTypeException moreThanOneValidatorFoundForType(Type type, String validatorClasses);

	@Message(id = 32, value = "Unable to initialize %s.")
	ValidationException unableToInitializeConstraintValidator(String validatorClassName, @Cause RuntimeException e);

	@Message(id = 33, value = "At least one custom message must be created if the default error message gets disabled.")
	ValidationException atLeastOneCustomMessageMustBeCreated();

	@Message(id = 34, value = "%s is not a valid Java Identifier.")
	IllegalArgumentException invalidJavaIdentifier(String identifier);

	@Message(id = 35, value = "Unable to parse property path %s.")
	IllegalArgumentException unableToParsePropertyPath(String propertyPath);

	@Message(id = 36, value = "Type %s not supported.")
	ValidationException typeNotSupported(Class<?> type);

	@Message(id = 37,
			value = "Inconsistent fail fast configuration. Fail fast enabled via programmatic API, but explicitly disabled via properties.")
	ValidationException inconsistentFailFastConfiguration();

	@Message(id = 38, value = "Invalid property path.")
	IllegalArgumentException invalidPropertyPath();

	@Message(id = 39, value = "Invalid property path. There is no property %1$s in entity %2$s.")
	IllegalArgumentException invalidPropertyPath(String propertyName, String beanClassName);

	@Message(id = 40, value = "Property path must provide index or map key.")
	IllegalArgumentException propertyPathMustProvideIndexOrMapKey();

	@Message(id = 41, value = "Call to TraversableResolver.isReachable() threw an exception.")
	ValidationException errorDuringCallOfTraversableResolverIsReachable(@Cause RuntimeException e);

	@Message(id = 42, value = "Call to TraversableResolver.isCascadable() threw an exception.")
	ValidationException errorDuringCallOfTraversableResolverIsCascadable(@Cause RuntimeException e);

	@Message(id = 43, value = "Unable to expand default group list %1$s into sequence %2$s.")
	GroupDefinitionException unableToExpandDefaultGroupList(List<?> defaultGroupList, List<?> groupList);

	@Message(id = 44, value = "At least one group has to be specified.")
	IllegalArgumentException atLeastOneGroupHasToBeSpecified();

	@Message(id = 45, value = "A group has to be an interface. %s is not.")
	ValidationException groupHasToBeAnInterface(String className);

	@Message(id = 46, value = "Sequence definitions are not allowed as composing parts of a sequence.")
	GroupDefinitionException sequenceDefinitionsNotAllowed();

	@Message(id = 47, value = "Cyclic dependency in groups definition")
	GroupDefinitionException cyclicDependencyInGroupsDefinition();

	@Message(id = 48, value = "Unable to expand group sequence.")
	GroupDefinitionException unableToExpandGroupSequence();

	@Message(id = 49, value = "The given index must be between %1$s and %2$s.")
	IndexOutOfBoundsException invalidIndex(String lowerBound, String upperBound);

	@Message(id = 50, value = "Missing format string in template: %s.")
	ValidationException missingFormatStringInTemplate(String expression);

	@Message(id = 51, value = "Invalid format: %s.")
	ValidationException invalidFormat(String message, @Cause IllegalFormatException e);

	@Message(id = 52,
			value = "Default group sequence and default group sequence provider cannot be defined at the same time.")
	GroupDefinitionException invalidDefaultGroupSequenceDefinition();

	@Message(id = 53, value = "'Default.class' cannot appear in default group sequence list.")
	GroupDefinitionException noDefaultGroupInGroupSequence();

	@Message(id = 54, value = "%s must be part of the redefined default group sequence.")
	GroupDefinitionException beanClassMustBePartOfRedefinedDefaultGroupSequence(String beanClassName);

	@Message(id = 55, value = "The default group sequence provider defined for %s has the wrong type")
	GroupDefinitionException wrongDefaultGroupSequenceProviderType(String beanClassName);

	@Message(id = 56, value = "Method %1$s doesn't have a parameter with index %2$d.")
	IllegalArgumentException invalidMethodParameterIndex(String method, int index);

	@Message(id = 57, value = "Unable to find constraints for  %s.")
	ValidationException unableToFindAnnotationConstraints(Class<? extends Annotation> annotationClass);

	@Message(id = 58, value = "Unable to read annotation attributes: %s.")
	ValidationException unableToReadAnnotationAttributes(Class<? extends Annotation> annotationClass, @Cause Exception e);

	@Message(id = 59, value = "Unable to retrieve annotation parameter value.")
	ValidationException unableToRetrieveAnnotationParameterValue(@Cause Exception e);

	@Message(id = 60, value = "Multiple definitions of default group sequence provider.")
	GroupDefinitionException multipleDefinitionOfDefaultGroupSequenceProvider();

	@Message(id = 61, value = "Multiple definitions of default group sequence.")
	GroupDefinitionException multipleDefinitionOfDefaultGroupSequence();

	@Message(id = 62,
			value = "Method %1$s has %2$s parameters, but the passed list of parameter meta data has a size of %3$s.")
	IllegalArgumentException invalidLengthOfParameterMetaDataList(Method method, int nbParameters, int listSize);

	@Message(id = 63, value = "Unable to instantiate %s.")
	ValidationException unableToInstantiate(String className, @Cause Exception e);

	ValidationException unableToInstantiate(Class<?> clazz, @Cause Exception e);

	@Message(id = 64, value = "Unable to instantiate %1$s: %2$s.")
	ValidationException unableToInstantiate(String message, Class<?> clazz, @Cause Exception e);

	@Message(id = 65, value = "Unable to load class: %s.")
	ValidationException unableToLoadClass(String className);

	ValidationException unableToLoadClass(String className, @Cause Exception e);

	@Message(id = 66, value = "Unable to instantiate Bean Validation provider %s.")
	ValidationException unableToInstantiateBeanValidationProvider(List<String> providerName, @Cause Exception e);

	@Message(id = 67, value = "Unable to read %s.")
	ValidationException unableToReadServicesFile(String servicesFileName, @Cause Exception e);

	@Message(id = 68, value = "No JSR 223 script engine found for language \"%s\".")
	ScriptException unableToFindScriptEngine(String languageName);

	@Message(id = 69, value = "Start index cannot be negative: %d.")
	IllegalArgumentException startIndexCannotBeNegative(int startIndex);

	@Message(id = 70, value = "End index cannot be negative: %d.")
	IllegalArgumentException endIndexCannotBeNegative(int endIndex);

	@Message(id = 71, value = "Invalid Range: %1$d > %2$d.")
	IllegalArgumentException invalidRange(int startIndex, int endIndex);

	@Message(id = 72, value = "A explicitly specified check digit must lie outside the interval: [%1$d, %2$d].")
	IllegalArgumentException invalidCheckDigit(int startIndex, int endIndex);

	@Message(id = 73, value = "'%c' is not a digit.")
	NumberFormatException characterIsNotADigit(char c);

	@Message(id = 74, value = "Parameters starting with 'valid' are not allowed in a constraint.")
	ConstraintDefinitionException constraintParametersCannotStartWithValid();

	@Message(id = 75, value = "%2$s contains Constraint annotation, but does not contain a %1$s parameter.")
	ConstraintDefinitionException constraintWithoutMandatoryParameter(String parameterName, String constraintName);

	@Message(id = 76,
			value = "%s contains Constraint annotation, but the payload parameter default value is not the empty array.")
	ConstraintDefinitionException wrongDefaultValueForPayloadParameter(String constraintName);

	@Message(id = 77, value = "%s contains Constraint annotation, but the payload parameter is of wrong type.")
	ConstraintDefinitionException wrongTypeForPayloadParameter(String constraintName, @Cause ClassCastException e);

	@Message(id = 78,
			value = "%s contains Constraint annotation, but the groups parameter default value is not the empty array.")
	ConstraintDefinitionException wrongDefaultValueForGroupsParameter(String constraintName);

	@Message(id = 79, value = "%s contains Constraint annotation, but the groups parameter is of wrong type.")
	ConstraintDefinitionException wrongTypeForGroupsParameter(String constraintName, @Cause ClassCastException e);

	@Message(id = 80,
			value = "%s contains Constraint annotation, but the message parameter is not of type java.lang.String.")
	ConstraintDefinitionException wrongTypeForMessageParameter(String constraintName);

	@Message(id = 81, value = "Overridden constraint does not define an attribute with name %s.")
	ConstraintDefinitionException overriddenConstraintAttributeNotFound(String attributeName);

	@Message(id = 82,
			value = "The overriding type of a composite constraint must be identical to the overridden one. Expected %1$s found %2$s.")
	ConstraintDefinitionException wrongAttributeTypeForOverriddenConstraint(String expectedReturnType, Class<?> currentReturnType);

	@Message(id = 83, value = "Wrong parameter type. Expected: %1$s Actual: %2$s.")
	ValidationException wrongParameterType(String expectedType, String currentType);

	@Message(id = 84, value = "The specified annotation defines no parameter '%s'.")
	ValidationException unableToFindAnnotationParameter(String parameterName, @Cause NoSuchMethodException e);

	@Message(id = 85, value = "Unable to get '%1$s' from %2$s.")
	ValidationException unableToGetAnnotationParameter(String parameterName, String annotationName, @Cause Exception e);

	@Message(id = 86, value = "No value provided for %s.")
	IllegalArgumentException noValueProvidedForAnnotationParameter(String parameterName);

	@Message(id = 87, value = "Trying to instantiate %1$s with unknown parameter(s): %2$s.")
	RuntimeException tryingToInstantiateAnnotationWithUnknownParameters(Class<?> annotationType, Set<String> unknownParameters);

	@Message(id = 88, value = "Property name cannot be null or empty.")
	IllegalArgumentException propertyNameCannotBeNullOrEmpty();

	@Message(id = 89, value = "Element type has to be FIELD or METHOD.")
	IllegalArgumentException elementTypeHasToBeFieldOrMethod();

	@Message(id = 90, value = "Member %s is neither a field nor a method.")
	IllegalArgumentException memberIsNeitherAFieldNorAMethod(Member member);

	@Message(id = 91, value = "Unable to access %s.")
	ValidationException unableToAccessMember(String memberName, @Cause Exception e);

	@Message(id = 92, value = "%s has to be a primitive type.")
	IllegalArgumentException hasToBeAPrimitiveType(Class<?> clazz);

	@Message(id = 93, value = "Unhandled primitive type.")
	RuntimeException unhandledPrimitiveType();

	@Message(id = 94, value = "null is an invalid type for a constraint validator.")
	ValidationException nullIsAnInvalidTypeForAConstraintValidator();

	@Message(id = 95, value = "Missing actual type argument for type parameter: %s.")
	IllegalArgumentException missingActualTypeArgumentForTypeParameter(TypeVariable<?> typeParameter);

	@Message(id = 96, value = "Unable to instantiate constraint factory class %s.")
	ValidationException unableToInstantiateConstraintFactoryClass(String constraintFactoryClassName, @Cause ValidationException e);

	@Message(id = 97, value = "Unable to open input stream for mapping file %s.")
	ValidationException unableToOpenInputStreamForMappingFile(String mappingFileName);

	@Message(id = 98, value = "Unable to instantiate message interpolator class %s.")
	ValidationException unableToInstantiateMessageInterpolatorClass(String messageInterpolatorClassName, @Cause Exception e);

	@Message(id = 99, value = "Unable to instantiate traversable resolver class %s.")
	ValidationException unableToInstantiateTraversableResolverClass(String traversableResolverClassName, @Cause Exception e);

	@Message(id = 100, value = "Unable to instantiate validation provider class %s.")
	ValidationException unableToInstantiateValidationProviderClass(String providerClassName, @Cause Exception e);

	@Message(id = 101, value = "Unable to parse %s.")
	ValidationException unableToParseValidationXmlFile(String file, @Cause JAXBException e);

	@Message(id = 102, value = "%s is not an annotation.")
	ValidationException isNotAnAnnotation(String annotationClassName);

	@Message(id = 103, value = "%s is not a constraint validator class.")
	ValidationException isNotAConstraintValidatorClass(Class<?> validatorClass);

	@Message(id = 104, value = "%s has already be configured in xml.")
	ValidationException beanClassHasAlreadyBeConfiguredInXml(String beanClassName);

	@Message(id = 105, value = "%1$s is defined twice in mapping xml for bean %2$s.")
	ValidationException isDefinedTwiceInMappingXmlForBean(String name, String beanClassName);

	@Message(id = 106, value = "%1$s does not contain the fieldType %2$s.")
	ValidationException beanDoesNotContainTheField(String beanClassName, String fieldName);

	@Message(id = 107, value = "%1$s does not contain the property %2$s.")
	ValidationException beanDoesNotContainTheProperty(String beanClassName, String getterName);

	@Message(id = 108, value = "Annotation of type %1$s does not contain a parameter %2$s.")
	ValidationException annotationDoesNotContainAParameter(String annotationClassName, String parameterName);

	@Message(id = 109, value = "Attempt to specify an array where single value is expected.")
	ValidationException attemptToSpecifyAnArrayWhereSingleValueIsExpected();

	@Message(id = 110, value = "Unexpected parameter value.")
	ValidationException unexpectedParameterValue();

	ValidationException unexpectedParameterValue(@Cause ClassCastException e);

	@Message(id = 111, value = "Invalid %s format.")
	ValidationException invalidNumberFormat(String formatName, @Cause NumberFormatException e);

	@Message(id = 112, value = "Invalid char value: %s.")
	ValidationException invalidCharValue(String value);

	@Message(id = 113, value = "Invalid return type: %s. Should be a enumeration type.")
	ValidationException invalidReturnType(Class<?> returnType, @Cause ClassCastException e);

	@Message(id = 114, value = "%s, %s, %s are reserved parameter names.")
	ValidationException reservedParameterNames(String messageParameterName, String groupsParameterName, String payloadParameterName);

	@Message(id = 115, value = "Specified payload class %s does not implement javax.validation.Payload")
	ValidationException wrongPayloadClass(String payloadClassName);

	@Message(id = 116, value = "Error parsing mapping file.")
	ValidationException errorParsingMappingFile(@Cause JAXBException e);
	
	/**
	 * The following lines are not a logging statement or an exception factory declaration but these messages have to be
	 * internationalized.
	 */

	@Message(value = "must not be null.")
	String mustNotBeNull();

	@Message(value = "%s must not be null.")
	String mustNotBeNull(String parameterName);

	@Message(value = "The parameter \"%s\" must not be null.")
	String parameterMustNotBeNull(String parameterName);

	@Message(value = "The parameter \"%s\" must not be empty.")
	String parameterMustNotBeEmpty(String parameterName);

	@Message(value = "The bean type cannot be null.")
	String beanTypeCannotBeNull();

	@Message(value = "null is not allowed as property path.")
	String propertyPathCannotBeNull();

	@Message(value = "The property name must not be empty.")
	String propertyNameMustNotBeEmpty();

	@Message(value = "null passed as group name.")
	String groupMustNotBeNull();

	@Message(value = "The bean type must not be null when creating a constraint mapping.")
	String beanTypeMustNotBeNull();

	@Message(value = "The method name must not be null.")
	String methodNameMustNotBeNull();

	@Message(value = "The object to be validated must not be null.")
	String validatedObjectMustNotBeNull();

	@Message(value = "The method to be validated must not be null.")
	String validatedMethodMustNotBeNull();

	@Message(value = "The class cannot be null.")
	String classCannotBeNull();

	@Message(value = "Class is null.")
	String classIsNull();
}
