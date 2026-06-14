package com.thughan.ipc.aidl.custom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thughan.ipc.aidl.common.Book;

import java.util.List;

/**
 * @Date : 2022/3/21
 * @Desc :  bookmanager
 * 也可以自定义实现AIDL
 * 1.DESCRIPTOR Binder的唯一标识,一般用当前Binder类名表示.
 * 2.asInterface(android.os.IBinder obj) 用于将服务端的Binder转换成客户端所需的AIDL接口类型的对象.
 * 这种转换过程是区分进程的,如果客户端和服务端位于同一进程,那么此方法返回的就是服务端的Stub对象本身,否则就是系统封装后的Stub.proxy对象.
 * 3.asBinder
 * 此方法用于返回当前Binder对象.
 * 4.onTransact
 * 这个方法运行在服务端中的Binder线程池中,当客户端发起请求,远程请求会通过底层后交由此方法来处理.
 * 5.Proxy#getBookList
 * 这个方法运行在客户端,当客户端远程调用此方法时,它的内部
 * 6.Proxy#addBook
 * 这个方法运行在客户端,它执行过程和getBookList一样.因为没有返回值所以不需要从_reply中取出返回值.
 **/
public interface IBookManager extends IInterface {

    static final String DESCRIPTOR = "com.thughan.ipc.aidl.custom.IBookManager";

    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;

    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList() throws RemoteException;

    public void addBook(Book book) throws RemoteException;

    public class Stub extends Binder implements IBookManager {

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBookManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && iin instanceof IBookManager) {
                return ((IBookManager) iin);
            }
            return new Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            // TODO: 2022/3/21 待实现
            return null;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            // TODO: 2022/3/21 待实现
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(DESCRIPTOR);
                    List<Book> result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(DESCRIPTOR);
                    Book arg0;
                    if (0 != data.readInt()) {
                        arg0 = Book.CREATOR.createFromParcel(data);
                    } else {
                        arg0 = null;
                    }
                    this.addBook(arg0);
                    reply.writeNoException();
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IBookManager {
            private IBinder mRemote;

            public Proxy(IBinder mRemote) {
                this.mRemote = mRemote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            public List<Book> getBookList() throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                List<Book> result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                    reply.readException();
                    result = reply.createTypedArrayList(Book.CREATOR);
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return result;
            }

            public void addBook(Book book) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    if (book != null) {
                        data.writeInt(1);
                        book.writeToParcel(data, 0);
                    } else {
                        data.writeInt(0);
                    }
                    mRemote.transact(TRANSACTION_addBook, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }

            }


        }

    }


}
