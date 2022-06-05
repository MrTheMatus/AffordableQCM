/* 
 * Copyright (C) 2015 Marco
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui4;

import java.util.NoSuchElementException; 

/**
 *
 * @author Marco
 */
public class ArrayCircularBuffer <T>{
    // internal data storage 
      public T[] data;  
      // indices for inserting and removing from queue  
      private int front = 0;  
      private int insertLocation = 0;  
      // number of elements in queue  
      private int size = 0;  
      /**  
       * Creates a circular buffer with the specified size.  
       *   
       * @param bufferSize  
       *      - the maximum size of the buffer  
       */  
      public ArrayCircularBuffer(int bufferSize) {  
           data = (T[]) new Object[bufferSize];  
      }  
      /**  
       * Inserts an item at the end of the queue. If the queue is full, the oldest  
       * value will be removed and head of the queue will become the second oldest  
       * value.  
       *   
       * @param item  
       *      - the item to be inserted  
       */  
      public synchronized void insert(T item) {  
           data[insertLocation] = item;  
           insertLocation = (insertLocation + 1) % data.length;  
           /**  
            * If the queue is full, this means we just overwrote the front of the  
            * queue. So increment the front location.  
            */  
           if (size == data.length) {  
                front = (front + 1) % data.length;  
           } else {  
                size++;  
           }  
      }  
       
      public synchronized int size() {  
           return size;  
      }  
       
      public synchronized T removeFront() {  
           if (size == 0) {  
                throw new NoSuchElementException();  
           }  
           T retValue = data[front];  
           front = (front + 1) % data.length;  
           size--;  
           return retValue;  
      }  
        
      public synchronized T peekFront() {  
           if (size == 0) {  
                return null;  
           } else {  
                return data[front];  
           }  
      }  
       
      public synchronized T peekLast() {  
           if (size == 0) {  
                return null;  
           } else {  
                int lastElement = insertLocation - 1;  
                if (lastElement < 0) {  
                     lastElement = data.length - 1;  
                }  
                return data[lastElement];  
           }  
      }
}
