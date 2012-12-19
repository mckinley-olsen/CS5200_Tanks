package GeneralPackage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Stack;

import MessagePackage.Reply.ReplyType;
import MessagePackage.Reply.Status;
import java.io.ObjectOutputStream;
import MessagePackage.DeterminableEnum;

/*
 * This class is converted from a C# class given from Dr. Clyde to the class to use in CS5200 homework 2, fall semester 2012
 * It was converted for use in Java by McKinley and, while it mostly functions the same as the original, does behave differently in some areas.
 */

public class ByteList 
{
    private final int SECTION_SIZE = 1024;

    private ArrayList<byte[]> _sections = new ArrayList<>();
    private int _addCurrentSection = 0;
    private int _addCurrentOffset = 0;
    private int _readCurrentPosition = 0;
    private Stack<Short> _readLimitStack = new Stack();

    //private int _maxPosition = -1;
    
    //added attributes
    private ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    private DataOutputStream stream = new DataOutputStream(byteStream);
    private ObjectOutputStream objectOutputStream;
    private ByteBuffer buffer;
    private Charset charset = Charset.forName("Unicode");
    private CharsetDecoder decoder = charset.newDecoder();
    
    
    public ByteList()
    {
        this._sections.add(new byte[SECTION_SIZE]);
    }

    public ByteList(Object...items)
    {
        this._sections.add(new byte[SECTION_SIZE]);
        this.addObjects(items);
    }
    
    public void addObjects(Object[] items)
    {
        for(Object item : items)
        {
            addObject(item);
        }
    }
    public void addObject(Object item)
    {
        if(item != null)
        {
            if (item instanceof Boolean)
            {
                //throw new NotImplementedException();
            }
            else if(item instanceof Byte)
            {
                this.add((byte)item);
            }
            else if(item instanceof Character)
            {
                this.add((char)item);
            }
            else if(item instanceof Double)
            {
                this.add((double)item);
            }
            else if(item instanceof Float)
            {
                this.add((float)item);
            }
            else if(item instanceof Short)
            {
                this.add((short)item);
            }
            else if(item instanceof Integer)
            {
                this.add((int)item);
            }
            else if(item instanceof Long)
            {
                this.add((long)item);
            }
            else if(item instanceof String)
            {
                this.add((String)item);
            }
            else if(item instanceof byte[])
            {
                this.add((byte[])item);
            }
            else
            {
                //throw new NotImplementedException();
                System.err.println("Unsupported type");
            }
        }
    }
    
    public void writeShortTo(int writePosition, short length)
    {
        if (writePosition >= 0 && writePosition < this.getLength() - 2)
        {
            int sectionIdx = writePosition / SECTION_SIZE;
            int sectionOffset = writePosition - sectionIdx * SECTION_SIZE;

            //byte[] bytes = BitConverter.GetBytes(IPAddress.HostToNetworkOrder(length));
            byte[] bytes = null;
            try 
            {
                stream.writeShort(length);
                bytes = byteStream.toByteArray();
               this.resetStreams();
            } 
            catch (IOException e) 
            {
                System.err.println("Error adding length");
                e.printStackTrace();
                System.exit(1);
            }
            
            System.arraycopy(   bytes, 0,                               // Source
                                _sections.get(sectionIdx), sectionOffset,   // Destination
                                2);                         // Length
        }
    }
    
    public void writeIntTo(int writePosition, int length)
    {
        if (writePosition >= 0 && writePosition < this.getLength() - 4)
        {
            int sectionIdx = writePosition / SECTION_SIZE;
            int sectionOffset = writePosition - sectionIdx * SECTION_SIZE;

            //byte[] bytes = BitConverter.GetBytes(IPAddress.HostToNetworkOrder(length));
            byte[] bytes = null;
            try 
            {
                stream.writeShort(length);
                bytes = byteStream.toByteArray();
               this.resetStreams();
            } 
            catch (IOException e) 
            {
                System.err.println("Error adding length");
                e.printStackTrace();
                System.exit(1);
            }
            
            System.arraycopy(   bytes, 0,                               // Source
                                _sections.get(sectionIdx), sectionOffset,   // Destination
                                4);                         // Length
        }
    }
    
    public short getCurrentWritePosition()
    {
        return (short)(_addCurrentSection * SECTION_SIZE + _addCurrentOffset);
    }
    public short getCurrentReadPosition()
    {
        return (short) _readCurrentPosition;
    }
    
    public byte getByte(int index)
    {
        if (index < 0)
        {
            //throw new ApplicationException();
        }
        byte[] section = getSection(index);
        return section[index];
    }

    public void setByte(int index, byte value)
    {
        grow(index);
        byte[] section = getSection(index);
        section[index] = value;
    }

    public void clear()
    {
        _sections.clear();
        _readLimitStack.clear();
        _addCurrentSection = 0;
        _addCurrentOffset = 0;
        _readCurrentPosition = 0;
    }

    public void resetRead()
    {
        _readCurrentPosition = 0;
    }

    public int getLength()
    {
        return _addCurrentSection * SECTION_SIZE + _addCurrentOffset;
    }

    private void grow(int additionBytesNeeded)
    {
        int sectionIndex = (additionBytesNeeded / SECTION_SIZE)+1;
        for (int i = this._sections.size() - 1; i < sectionIndex; i++)
            this._sections.add(new byte[SECTION_SIZE]);
    }

    private byte[] getSection(int index)
    {
        int sectionIndex = index / SECTION_SIZE;

        if (sectionIndex >= _sections.size())
        {
            //throw new ApplicationException();
        }
            
        index -= sectionIndex * SECTION_SIZE;
        return _sections.get(sectionIndex);
    }

    public void add(ByteList value)
    {
        if(value != null)
        {
            for (int i = 0; i <= value._addCurrentSection; i++)
            {
                if (i < value._addCurrentSection)
                    this.add(value._sections.get(i), 0, SECTION_SIZE);
                else
                    this.add(value._sections.get(i), 0, value._addCurrentOffset);
            }
        }
    }

    public void add(byte value)
    {
        add(new byte[] { value });
    }

    public void add(boolean value)
    {
        if (value)
        {
            add(new byte[] { 1 });
        }
        else
        {
            add(new byte[] { 0 });
        }
    }

    public void add(char value)
    {
        try 
        {
            stream.writeChar(value);
            this.add(byteStream.toByteArray());
            this.resetStreams();
        } 
        catch (IOException e) 
        {
            System.err.println("Error adding short");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void add(short value)
    {
        try 
        {
            stream.writeShort(value);
            this.add(byteStream.toByteArray());
            this.resetStreams();
        } 
        catch (IOException e) 
        {
            System.err.println("Error adding short");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void add(int value)
    {
        try 
        {
            stream.writeInt(value);
            byte[] b = byteStream.toByteArray();            
            this.add(byteStream.toByteArray());
            this.resetStreams();
        } 
        catch (IOException e) 
        {
            System.err.println("Error adding int");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void add(long value)
    {
        try 
        {
            stream.writeLong(value);
            this.add(byteStream.toByteArray());
           this.resetStreams();
        } 
        catch (IOException e) 
        {
            System.err.println("Error adding short");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void add(double value)
    {
        try 
        {
            stream.writeDouble(value);
            this.add(byteStream.toByteArray());
           this.resetStreams();
        } 
        catch (IOException e) 
        {
            System.err.println("Error adding short");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void add(float value)
    {
        try 
        {
            stream.writeFloat(value);
            this.add(byteStream.toByteArray());
           this.resetStreams();
        } 
        catch (IOException e) 
        {
            System.err.println("Error adding short");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void add(String value)
    {
        if (value != null)
        {
            this.add(value.length());
            //this.add(value.getBytes());
            for(int count=0;count<value.length();++count)
            {
                this.add(value.charAt(count));
            }
        }
        else
        {
            this.add((short) 0);
        }
    }

    public void add(byte[] value)
    {
        grow(_addCurrentOffset + value.length);
        //System.out.println(value.length);
        int cnt = 0;
        while(cnt < value.length)
        {
            byte[] section = _sections.get(_addCurrentSection);

            short blockSize = (short)Math.min(SECTION_SIZE - _addCurrentOffset, value.length - cnt);
            System.arraycopy(value, cnt, section, _addCurrentOffset, blockSize); //untested, but I think this functions the same as C#'s blockcopy

            cnt += blockSize;
            _addCurrentOffset += blockSize;
            if (_addCurrentOffset == SECTION_SIZE)
            {
                _addCurrentOffset = 0;
                _addCurrentSection++;

                if (_addCurrentSection >= _sections.size())
                    _sections.add(new byte[SECTION_SIZE]);
            }
        }
    }

    public void add(byte[] value, int offset, int length)
    {
        grow(_addCurrentOffset + length);

        int cnt = 0;
        while (cnt < length)
        {
            byte[] section = _sections.get(_addCurrentSection);

            short blockSize = (short)Math.min(SECTION_SIZE - _addCurrentOffset, length - cnt);
            System.arraycopy(value, offset + cnt, section, _addCurrentOffset, blockSize); //untested, but I think this functions the same as C#'s blockcopy

            cnt += blockSize;
            _addCurrentOffset += blockSize;
            if (_addCurrentOffset == SECTION_SIZE)
            {
                _addCurrentOffset = 0;
                _addCurrentSection++;

                if (_addCurrentSection >= _sections.size())
                    _sections.add(new byte[SECTION_SIZE]);
            }
        }
    }
    
    private void resetStreams()
    {
        this.byteStream = new ByteArrayOutputStream();
        this.stream = new DataOutputStream(byteStream);
    }
    
// <editor-fold defaultstate="collapsed" desc=" unimplemented enum serialization ">
//serialize enum
    public void add(ReplyType type) {
        /*
         try
         {
         this.objectOutputStream = new ObjectOutputStream(byteStream);
         this.objectOutputStream.writeObject(type);
         this.add(byteStream.toByteArray());
         }
         catch(IOException e)
         {
            
         }
         */
        
    }
    //serialize enum

    public void add(Status status) {
        /*
         try
         {
         this.objectOutputStream = new ObjectOutputStream(byteStream);
         this.objectOutputStream.writeObject(status);
         this.add(byteStream.toByteArray());
         }
         catch(IOException e)
         {
            
         }
         */
    }

// </editor-fold>
    
    public DeterminableEnum getEnum(DeterminableEnum type)
    {
        DeterminableEnum[] values = type.getValues();
        int ordinal = this.getInt();
        for(DeterminableEnum value : values)
        {
            if(value.ordinal() == ordinal)
            {
                System.out.println("Setting to: "+value);
                return value;
            }
        }
        return null;
    }

    public ByteList getByteList(int length)
    {
        ByteList result = new ByteList();
        result.fromBytes(this.getBytes(length));
        return result;
    }

    public byte getByte()
    {
        return this.getBytes(1)[0];
    }

    public boolean getBool()
    {
        return (this.getBytes(1)[0] == 0) ? false : true;
    }

    public char getChar()
    {
        this.buffer.clear();
        this.buffer = ByteBuffer.wrap(this.getBytes(2));        
        return buffer.getChar();
    }

    public short getShort()
    {
        this.buffer.clear();
        this.buffer = ByteBuffer.wrap(this.getBytes(2));
        return buffer.getShort();
    }

    public short peekShort()
    {
        this.buffer = ByteBuffer.wrap(this.getBytes(2));
        short result = buffer.getShort();
        this._readCurrentPosition -= 2;
        return result;
    }
    
    public int peekInt()
    {
        this.buffer = ByteBuffer.wrap(this.getBytes(4));
        int result = buffer.getInt();
        this._readCurrentPosition -= 4;
        return result;
    }

    public int getInt()
    {
        this.buffer.clear();
        this.buffer = ByteBuffer.wrap(this.getBytes(4));
        return buffer.getInt();
    }

    public long getLong()
    {
        this.buffer.clear();
        this.buffer = ByteBuffer.wrap(this.getBytes(8));
        return buffer.getLong();
    }

    public double getDouble()
    {
        this.buffer.clear();
        this.buffer = ByteBuffer.wrap(this.getBytes(8));
        return buffer.getDouble();
    }

    public float getFloat()
    {
        this.buffer.clear();
        this.buffer = ByteBuffer.wrap(this.getBytes(4));
        return buffer.getFloat();
    }

    public String getString()
    {
        /*
        this.decoder.reset();
        String result = "";
        short length = getShort();
        if (length>0)
        {
            this.buffer.clear();
            //this.buffer = ByteBuffer.wrap(this.getBytes(length));
            System.out.println(this.buffer.getChar(0));
            try
            {
                result = this.decoder.decode(buffer).toString();
                this.decoder.flush(CharBuffer.allocate(2));
            }
            catch(CharacterCodingException e)
            {
                System.err.println("Error getting String");
                e.printStackTrace();
                System.exit(1);
            }
        }
        return result;
        * */
        String result = "";
        
        int length = getInt();
        
        char[] temp = new char[length];
        for(int count=0;count<length;++count)
        {
            temp[count] = this.getChar();
        }
        
        return String.valueOf(temp).trim();
    }

    public byte[] getBytes(int length)
    {
        if(_readLimitStack.size() > 0 && _readCurrentPosition + length > _readLimitStack.peek())
        {
            System.err.println("Attempt to read beyond read limit");
        }
        
        byte[] result = new byte[length];
        int bytesRead = 0;
        int sectionOffset = _readCurrentPosition;

        while (bytesRead < length)
        {
            int sectionIndex = _readCurrentPosition / SECTION_SIZE;

            sectionOffset = _readCurrentPosition - sectionIndex * SECTION_SIZE;
            
            int cnt = Math.min(SECTION_SIZE - sectionOffset, length - bytesRead);
            
            System.arraycopy(_sections.get(sectionIndex), sectionOffset, result, bytesRead, cnt);

            sectionOffset = 0;
            _readCurrentPosition += (short)cnt;
            bytesRead += cnt;
        }

        return result;
    }

    public byte[] toBytes()
    {
        int bytesRead = 0;
        byte[] bytes = new byte[this.getLength()];

        for (int i = 0; i <= _addCurrentSection; i++)
        {
            int sectionBytes = SECTION_SIZE;
            if (i == _addCurrentSection)
            {
                sectionBytes = _addCurrentOffset;
            }
            System.arraycopy(_sections.get(i), 0, bytes, bytesRead, sectionBytes);
            bytesRead += sectionBytes;
        }

        return bytes;
    }

    public void fromBytes(byte[] bytes)
    {
        this.clear();
        this.add(bytes);
    }

    /*
    public int SetMaxPosition(int length)
    {
        int oldMaxPosition = _maxPosition;
        _maxPosition = _readCurrentPosition + length;
        return oldMaxPosition;
    }

    public void RestoreMaxPosition(int maxPosition)
    {
        _maxPosition = maxPosition;
    }

    public void ClearMaxPosition()
    {
        _maxPosition = -1;
    }
    */
    
    public void setNewReadLimit(short length)
    {
        _readLimitStack.push((short)(_readCurrentPosition + length));
    }

    public void restorePreviousReadLimit()
    {
        if (_readLimitStack.size() > 0)
            _readLimitStack.pop();
    }

    public void ClearMaxReadPosition()
    {
        _readLimitStack.clear();
    }

    public boolean isMore()
    {
        int tmpMax = (_readLimitStack.size() == 0) ? this.getLength() : _readLimitStack.peek();
        return (_readCurrentPosition >= tmpMax) ? false : true;
    }

    //# region Byte Parsing Functions
    public static String getString(byte[] bytes, Integer offset, boolean isNullTerminated)
    {
        /*
        UInt16 length = BitConverter.ToUInt16(bytes, offset);
        
        offset += 2;
        String result = Encoding.ASCII.GetString(bytes, offset, length);
        offset += length;
        if(isNullTerminated)
            offset++;
        return result;
        */
        System.err.println("Use of unsupported static method getString");
        return "Unsupported";
    }
    //# endregion
}
