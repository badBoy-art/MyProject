package com.snow.spi.service.impl;

import com.snow.spi.service.DogService;

import java.util.ServiceLoader;

/**
 * spi测试
 *
 * @author xuedui.zhao
 * @create 2018-04-26
 */
public class TestSpi {

  public static void main(String args[]) {
    ServiceLoader<DogService> loaders = ServiceLoader.load(DogService.class);
    for (DogService d : loaders) {
      d.sleep();
    }
  }
}
