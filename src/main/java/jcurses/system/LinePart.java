package jcurses.system;

class LinePart {

	int _begin = -1;
	int _end = -2;
	int _alignment = -1;
	int _position = -1;

	public LinePart() {
	}

	LinePart(int begin, int end, int alignment) {
		_begin = begin;
		_end = end;
		_alignment = alignment;
	}


	LinePart(int begin, int end, int position, int alignment) {
		_begin = begin;
		_end = end;
		_position = position;
		_alignment = alignment;
	}

	boolean isEmpty() {
		return (_begin > _end);
	}

	public String toString() {
		return "[begin="+_begin+",end="+_end+",align="+_alignment+",isEmpty="+isEmpty();
	}
}
