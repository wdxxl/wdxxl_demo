package com.wdxxl.jdk.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListRemove {

	public static void main(String args[]) {
		ListRemove lr = new ListRemove();
        // lr.listRemove();
        // lr.listRemoveBreak();
        lr.listRemove2();
        // lr.iteratorRemove();
	}

	/**
	 * 使用增强的for循环
	 * 在循环过程中从List中删除元素以后，继续循环List时会报ConcurrentModificationException
	 */
	public void listRemove() {
		List<Student> students = getStudents();
		for (Student stu : students) {
			if (stu.getId() == 2) {
                students.remove(stu);
            }
		}
	}

	/**
	 * 像这种使用增强的for循环对List进行遍历删除，但删除之后马上就跳出的也不会出现异常
	 */
	public void listRemoveBreak() {
		List<Student> students = getStudents();
		for (Student stu : students) {
			if (stu.getId() == 2) {
				students.remove(stu);
				break;
			}
		}
	}

	/**
	 * 这种不使用增强的for循环，每次重新获取list的size遍历的情况运行时不会报错，但是可能删除的结果是错的。
	 */
	public void listRemove2() {
		List<Student> students = getStudents();
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).getId()%2 == 0) {
                students.remove(i);
            }
		}
        System.out.println(students);
	}

	/**
	 * 使用Iterator的方式也可以顺利删除和遍历
	 */
	public void iteratorRemove() {
		List<Student> students = getStudents();
		System.out.println(students);
		Iterator<Student> stuIter = students.iterator();
		while (stuIter.hasNext()) {
			Student student = stuIter.next();
			if (student.getId() % 2 == 0) {
                stuIter.remove();
            }
		}
		System.out.println(students);
	}

	private List<Student> getStudents() {
		List<Student> students = new ArrayList<Student>() {
			{
				int i = 0;
				while (i++ < 10) {
					Student student = new Student(i, "201200" + i, "name_" + i);
					this.add(student);
				}
			}
		};
		return students;
	}
}
