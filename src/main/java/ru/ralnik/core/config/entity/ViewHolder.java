/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.core.config.entity;

import javafx.scene.Parent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс - оболочка: контроллер мы обязаны указать в качестве бина,
 * а view - представление, нам предстоит использовать в точке входа {@link ru.ralnik.HotelApplication}.
 */
@Data
@AllArgsConstructor
public class ViewHolder {
    private Parent view;
    private Object controller;
}
