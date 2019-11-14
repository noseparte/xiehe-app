package sample.Works

import javafx.beans.InvalidationListener
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.control.CheckBox

class SuperCheckBox {
    private var mCheck = CheckBox()

    fun isSelect() = mCheck.isSelected

    fun getCheckBox(): ObservableValue<CheckBox> {
        return object : ObservableValue<CheckBox> {
            override fun removeListener(listener: ChangeListener<in CheckBox>?) {

            }

            override fun removeListener(listener: InvalidationListener?) {
            }

            override fun addListener(listener: ChangeListener<in CheckBox>?) {
            }

            override fun addListener(listener: InvalidationListener?) {
            }

            override fun getValue() = mCheck
        }
    }
}