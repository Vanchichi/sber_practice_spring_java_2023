package ru.sber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Конфигуратор
 */

@Configuration
@ComponentScan(basePackages = {"ru.sber.aspects", "ru.sber.services", "ru.sber.repositories"})
@EnableAspectJAutoProxy
public class ProjectConfig {

}