package com.github.platan.idea.dependencies.sort

import com.intellij.testFramework.LightCodeInsightTestCase

class SortDependenciesTest extends LightCodeInsightTestCase {

    @Override
    protected String getTestDataPath() {
        'src/test/resources/actions/sort/'
    }

    void test__already_sorted() {
        doTest()
    }

    void test__simple() {
        doTest()
    }

    private doTest() {
        def fileName = getTestName(false).replaceFirst('__', '')
        configureByFile("${fileName}.gradle")
        SortDependenciesAction action = new SortDependenciesAction()
        action.handler.invoke(project, editor, file)
        checkResultByFile("${fileName}_after.gradle")
    }

}