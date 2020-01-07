# FormLayoutManager

## 介绍
#### [博客介绍地址](https://blog.csdn.net/DNWalter/article/details/103744584)
#### RecyclerView实现表格样式，而自定义的一个LayoutManager。用LayoutManager实现表格，与平时用HorizontalScrollView嵌套RecyclerView来实现表格的区别就是：
#### 1、LayoutManager实现表格，由始至终就只有一个RecyclerView，没有多余的嵌套；
#### 2、LayoutManager实现表格，手指滑动的时候可以向任意方向滑动。而以前的方法当你的手指进行水平滑动时，只要手指不松开，是不能进行垂直的滑动的；
#### 3、LayoutManager实现表格，每一格的控件都会复用。而我们知道用HorizontalScrollView嵌套RecyclerView时，只有垂直滑动，每一行会进行复用，而水平滑动没有复用一说，因为水平滑动滑的是HorizontalScrollView。
#### ~~4、LayoutManager实现表格，使用有一个前提，就是你的这个表格必须每一格的宽高要一样。如果你的表格不是每一格宽高一样，如有一些列要比较宽一点，这类需求的话，你还是要用回以前的嵌套方法来实现；~~
#### *上面说的区别，我会以博客给大家详细的讲解*

## 导入
#### 1、在最外层目录的build.gradle添加如下maven
    allprojects {
	    repositories {
    	...
		maven { url 'https://jitpack.io' }
	    }
	}
#### 2、在要使用这个FormLayoutManager的module的build.gradle添加依赖(后面v2.0的版本号，可以根据releases里面最新的版本进行替换)
    dependencies {
	        implementation 'com.github.dnwalter:FormLayoutManager:v2.0'
	}

## 使用
#### 下面是常用的简单使用的代码(以一个水平表格为例子，我这里说的水平表格指的是以表格每一行为一个实体对象的表格)
    FormLayoutManager layoutManager = new FormLayoutManager(8);
    mRecyclerView.setLayoutManager(layoutManager);
#### FormLayoutManager默认要传入一个整型，这个整型代表的是这个表格有多少列。后面RecyclerView可以如常设置adapter，但adapter要继承一个基类
    public class MonsterHAdapter extends BaseHFormAdapter<Monster> {

    @Override
    protected String[] getRowDatas(Monster model) {
        return new String[]{model.getName(), model.getAttribute(), model.getLv(), model.getAtk(),
                model.getDef(), model.getRace(), model.getType1(), model.getType2()};
    }

    @Override
    public int getColumnCount() {
        return 8;
    }
    }
#### 其他要重写的方法可看源码或看demo来了解怎么配置布局和数据，主要要重写是上面的两个方法
#### 1、getRowDatas，你的实体哪个属性按第一列到最后列设置好
#### 2、getColumnCount，设置这个表格的列数

### 以上步骤基本可以水平表格的使用，后面我会用博客来详细说明使用过程和实现的原理。现在大家可以自行看一下源码或demo了解一下先。
