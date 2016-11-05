#!/bin/bash
########################################################

#########################################################

# 切换目录

  cd "$(dirname "$0")";
  echo "切换到打包目录";
  cd ../html/;
  echo "开始打包基础CSS文件";

 
#sass执行 
  sass sass/reset.scss css/reset.css --style compressed
  sass sass/login.scss css/login.css --style compressed
  sass sass/layout.scss css/layout.css --style compressed
  
