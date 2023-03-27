package com.example.toyexchange.theme.ui.adapter

import com.example.toyexchange.Domain.model.Toy

class OnClickListener(val clickListener: (toy: Toy) -> Unit) {
    fun onClick(toy: Toy) = clickListener(toy)
}
