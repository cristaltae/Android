/*
 * Copyright (c) 2019 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.app.onboarding.ui

import com.duckduckgo.app.browser.defaultbrowsing.DefaultBrowserDetector
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OnboardingPageManagerTest {

    private lateinit var testee: OnboardingPageManager
    private val onboardingPageBuilder: OnboardingPageBuilder = mock()
    private val mockDefaultBrowserDetector: DefaultBrowserDetector = mock()

    @Before
    fun setup() {
        testee = OnboardingPageManagerWithTrackerBlocking(onboardingPageBuilder, mockDefaultBrowserDetector)
    }

    @Test
    fun whenDDGIsNotDefaultBrowserThenExpectedOnboardingPagesAreTwo() {
        configureDeviceSupportsDefaultBrowser()
        whenever(mockDefaultBrowserDetector.isDefaultBrowser()).thenReturn(false)

        testee.buildPageBlueprints()

        assertEquals(2, testee.pageCount())
    }

    @Test
    fun whenDDGAsDefaultBrowserThenSinglePageOnBoarding() {
        configureDeviceSupportsDefaultBrowser()
        whenever(mockDefaultBrowserDetector.isDefaultBrowser()).thenReturn(true)

        testee.buildPageBlueprints()

        assertEquals(1, testee.pageCount())
    }

    @Test
    fun whenDeviceDoesNotSupportDefaultBrowserThenSinglePageOnBoarding() {
        configureDeviceDoesNotSupportDefaultBrowser()

        testee.buildPageBlueprints()

        assertEquals(1, testee.pageCount())
    }

    private fun configureDeviceSupportsDefaultBrowser() {
        whenever(mockDefaultBrowserDetector.deviceSupportsDefaultBrowserConfiguration()).thenReturn(true)
    }

    private fun configureDeviceDoesNotSupportDefaultBrowser() {
        whenever(mockDefaultBrowserDetector.deviceSupportsDefaultBrowserConfiguration()).thenReturn(false)
    }
}