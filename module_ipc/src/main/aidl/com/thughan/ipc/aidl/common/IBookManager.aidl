// IBookManager.com.thughan.ipc.aidl
package com.thughan.ipc.aidl.common;

import com.thughan.ipc.aidl.common.Book;

// Declare any non-default types here with import statements

interface IBookManager {
//    /**  生成的这句只是展示可以传递的例子
//     * Demonstrates some basic types that you can use as parameters
//     * and return values in AIDL.
//     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    List<Book> getBookList();
    void addBookInOut(inout Book book);

}