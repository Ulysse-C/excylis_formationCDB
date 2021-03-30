package com.excilys.formationcdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.excilys.formationcdb.controller.cli", "com.excilys.formationcdb.ui.cli"})
public class CliConfig {

}
