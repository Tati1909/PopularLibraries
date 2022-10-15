package com.example.popularlibraries

import com.example.popularlibraries.view.details.DetailsFragment
import com.example.popularlibraries.view.info.InfoFragment
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class FragmentArgumentsTest {

    @Test
    fun checkIsNullFragmentDetailArgument() {
        assertNotNull(DetailsFragment.ARG_USER)
    }

    @Test
    fun checkIsNullFragmentInfoArgument() {
        assertNotNull(InfoFragment.ARG_REPO_URL)
    }

    @Test
    fun checkIsNotEqualsFragmentArguments() {
        assertNotEquals(DetailsFragment.ARG_USER, InfoFragment.ARG_REPO_URL)
    }

    @Test
    fun checkIsSameFragmentArguments() {
        assertFalse(DetailsFragment.ARG_USER == InfoFragment.ARG_REPO_URL)
    }
}