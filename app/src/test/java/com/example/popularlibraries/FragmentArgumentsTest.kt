package com.example.popularlibraries

import com.example.popularlibraries.view.details.DetailFragment
import com.example.popularlibraries.view.info.InfoFragment
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FragmentArgumentsTest {

    @Test
    fun checkIsNullFragmentDetailArgument() {
        assertNotNull(DetailFragment.ARG_USER)
    }

    @Test
    fun checkIsNullFragmentInfoArgument() {
        assertNotNull(InfoFragment.ARG_REPO_URL)
    }

    @Test
    fun checkIsNotEqualsFragmentArguments() {
        assertNotEquals(DetailFragment.ARG_USER, InfoFragment.ARG_REPO_URL)
    }

    @Test
    fun checkIsSameFragmentArguments() {
        assertFalse(DetailFragment.ARG_USER == InfoFragment.ARG_REPO_URL)
    }
}