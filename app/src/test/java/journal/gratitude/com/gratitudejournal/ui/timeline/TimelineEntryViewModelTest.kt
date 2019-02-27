package journal.gratitude.com.gratitudejournal.journal.gratitude.com.gratitudejournal.ui.timeline

import android.view.View
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import journal.gratitude.com.gratitudejournal.R
import journal.gratitude.com.gratitudejournal.model.Entry
import journal.gratitude.com.gratitudejournal.ui.timeline.TimelineAdapter
import journal.gratitude.com.gratitudejournal.ui.timeline.TimelineEntryViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class TimelineEntryViewModelTest {

    private val onClickListener = mock<TimelineAdapter.OnClickListener>()
    private val date = LocalDate.of(2011, 11, 11)
    private val content = "hiiiiiiii"
    private val viewModel = TimelineEntryViewModel(
        Entry(date, content),
        false,
        onClickListener
    )

    @Test
    fun onClick_callsClickListener() {
        viewModel.onClick(mock())

        verify(onClickListener).onClick(date)
    }

    @Test
    fun isCurrentDate_currentDate_visible() {
        val today = LocalDate.now()
        val viewModel = TimelineEntryViewModel(
            Entry(today, content),
            false,
            onClickListener
        )

        val actual = viewModel.isCurrentDate()
        val expected = View.VISIBLE

        assertEquals(expected, actual)
    }

    @Test
    fun getHintText_currentDate_presentTense() {
        val today = LocalDate.now()
        val viewModel = TimelineEntryViewModel(
            Entry(today, content),
            false,
            onClickListener
        )

        val actual = viewModel.hintText.get()
        val expected = R.string.what_are_you_thankful_for_today

        assertEquals(expected, actual)
    }

    @Test
    fun getHintText_yesterday_pastTense() {
        val yesterday = LocalDate.now().minusDays(1)
        val viewModel = TimelineEntryViewModel(
            Entry(yesterday, content),
            false,
            onClickListener
        )

        val actual = viewModel.hintText.get()
        val expected = R.string.what_are_you_thankful_for_yesterday

        assertEquals(expected, actual)
    }

    @Test
    fun isCurrentDate_notCurrentDate_gone() {
        val actual = viewModel.isCurrentDate()
        val expected = View.GONE

        assertEquals(expected, actual)
    }

    @Test
    fun isEmptyState_emptyState_visible() {
        val content = ""
        val viewModel = TimelineEntryViewModel(
            Entry(date, content),
            false,
            onClickListener
        )

        val actual = viewModel.isEmptyState()
        val expected = View.VISIBLE

        assertEquals(expected, actual)
    }

    @Test
    fun isEmptyState_notEmptyState_gone() {
        val actual = viewModel.isEmptyState()
        val expected = View.GONE

        assertEquals(expected, actual)
    }

    @Test
    fun isTailVisible_notLastItem_gone() {
        val actual = viewModel.isTailVisible()
        val expected = View.GONE

        assertEquals(expected, actual)
    }


    @Test
    fun isTailVisible_LastItem_visible() {
        val viewModel = TimelineEntryViewModel(
            Entry(date, content),
            true,
            onClickListener
        )

        val actual = viewModel.isTailVisible()
        val expected = View.VISIBLE

        assertEquals(expected, actual)
    }

    @Test
    fun getDate_returns_fullString() {
        val expected = "November 11, 2011"
        val actual = viewModel.date

        assertEquals(expected, actual)
    }

    @Test
    fun getContent_returns_content() {
        val actual = viewModel.content

        assertEquals(content, actual)
    }
}