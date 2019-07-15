package com.justd.rabo.issues

import android.content.Context
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import com.justd.rabo.R
import com.justd.rabo.app.model.Issue
import kotterknife.bindView

class IssueWidget(context: Context) : CardView(context) {

    private val nameTextView by bindView<TextView>(R.id.name)
    private val birthdayTextView by bindView<TextView>(R.id.birth_date)
    private val issueCountTextView by bindView<TextView>(R.id.issues_count)

    init {
        View.inflate(context, R.layout.widget_issue, this)
    }

    fun bind(issue: Issue) {
        nameTextView.text = "${issue.firstame} ${issue.surname}"
        birthdayTextView.text = issue.dateOfBirth
        issueCountTextView.text = context.getString(R.string.issues_count, issue.issueCount)
    }

}