package journal.gratitude.com.gratitudejournal.ui.timeline

import android.view.View
import androidx.databinding.ObservableInt
import journal.gratitude.com.gratitudejournal.R
import journal.gratitude.com.gratitudejournal.model.Entry
import journal.gratitude.com.gratitudejournal.ui.bindingadapter.Visibility
import journal.gratitude.com.gratitudejournal.util.toFullString
import org.threeten.bp.LocalDate

class TimelineEntryViewModel(
        private val timelineItem: Entry,
        private val isLastItem: Boolean,
        private val clickListener: TimelineAdapter.OnClickListener
) {

    val date = timelineItem.entryDate.toFullString()
    val content = timelineItem.entryContent
    val hintText = ObservableInt()

    init {
        val today = LocalDate.now()
        if (timelineItem.entryDate == today) {
            hintText.set(R.string.what_are_you_thankful_for_today)
        } else if (timelineItem.entryDate == today.minusDays(1)) {
            hintText.set(R.string.what_are_you_thankful_for_yesterday)
        }
    }

    fun onClick(view: View) {
        clickListener.onClick(timelineItem.entryDate)
    }

    @Visibility
    fun isCurrentDate(): Int {
        return if (timelineItem.entryDate == LocalDate.now()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @Visibility
    fun isEmptyState(): Int {
        return if (timelineItem.entryContent.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @Visibility
    fun isTailVisible(): Int {
        return if (isLastItem) View.VISIBLE else View.GONE
    }
}