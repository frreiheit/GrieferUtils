package dev.l3g7.griefer_utils.misc.mxparser.syntaxchecker;

@SuppressWarnings("unused")public class SyntaxCheckerTokenManager implements SyntaxCheckerConstants {

private  int jjStopStringLiteralDfa_0(long active0, long active1){
   if ((active1 & 0x10L) != 0L)
         return 66;
   if ((active0 & 0x8000000000000L) != 0L)
      return 16;
   if ((active0 & 0x1000000000000L) != 0L)
      return 248;
   if ((active0 & 0x2000000000000L) != 0L || (active1 & 0x140L) != 0L)
      return 249;
   if ((active0 & 0xc000000000000000L) != 0L || (active1 & 0xa80L) != 0L)
      return 3;
   if ((active0 & 0x80000000000000L) != 0L)
      return 5;
   if ((active1 & 0x2000000000000000L) != 0L)
      return 62;
   if ((active1 & 0x20L) != 0L)
   {
      jjmatchedKind = 79;
      return 53;
   }
   return -1;
}
private  int jjStartNfa_0(long active0, long active1){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(active0, active1), 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 33:
         return jjStartNfaWithStates_0(55, 5);
      case 35:
         return jjStopAtPos(0, 54);
      case 37:
         return jjStopAtPos(0, 56);
      case 40:
         jjmatchedKind = 46;
         return jjMoveStringLiteralDfa1_0(0x0L, 0x4000L);
      case 41:
         return jjStopAtPos(0, 47);
      case 42:
         return jjStopAtPos(0, 50);
      case 43:
         return jjStartNfaWithStates_0(48, 248);
      case 44:
         return jjStopAtPos(0, 57);
      case 45:
         jjmatchedKind = 49;
         return jjMoveStringLiteralDfa1_0(0x0L, 0x140L);
      case 47:
         return jjStartNfaWithStates_0(51, 16);
      case 59:
         return jjStopAtPos(0, 58);
      case 60:
         jjmatchedKind = 62;
         return jjMoveStringLiteralDfa1_0(0x8000000000000000L, 0xa80L);
      case 62:
         jjmatchedKind = 64;
         return jjMoveStringLiteralDfa1_0(0x0L, 0x2L);
      case 64:
         return jjMoveStringLiteralDfa1_0(0x0L, 0x20L);
      case 91:
         return jjStartNfaWithStates_0(125, 62);
      case 93:
         return jjStopAtPos(0, 126);
      case 94:
         jjmatchedKind = 52;
         return jjMoveStringLiteralDfa1_0(0x20000000000000L, 0x0L);
      case 126:
         return jjStartNfaWithStates_0(68, 66);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0, long active1){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(active0, active1);
      return 1;
   }
   switch(curChar)
   {
      case 43:
      case 45:
      case 47:
         return jjMoveStringLiteralDfa2_0(active0);
      case 61:
         if ((active0 & 0x8000000000000000L) != 0L)
            return jjStopAtPos(1, 63);
         else if ((active1 & 0x2L) != 0L)
            return jjStopAtPos(1, 65);
         break;
      case 94:
         if ((active0 & 0x20000000000000L) != 0L)
            return jjStopAtPos(1, 53);
         break;
      case 126:
         if ((active1 & 0x20L) != 0L)
            return jjStopAtPos(1, 69);
         break;
      default :
         break;
   }
   return jjStartNfa_0(active0, active1);
}
private int jjMoveStringLiteralDfa2_0(long old0){
   long active1 = 0;
   return jjStartNfa_0(old0, 0);
}
private int jjStartNfaWithStates_0(int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = 0;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return 1; }
   return jjMoveNfa_0(state, 1);
}
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 248;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 66:
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 69;
                  else if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 67;
                  else if (curChar == 61)
                  {
                     if (kind > 61)
                        kind = 61;
                  }
                  if (curChar == 38)
                  {
                     if (kind > 74)
                        kind = 74;
                  }
                  break;
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 82)
                        kind = 82;
                     { jjCheckNAddStates(0, 7); }
                  }
                  else if ((0x8c00c09800000000L & l) != 0L)
                  {
                     if (kind > 79)
                        kind = 79;
                  }
                  else if (curChar == 45)
                     { jjCheckNAddStates(8, 11); }
                  else if (curChar == 43)
                     { jjCheckNAddStates(12, 15); }
                  else if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 14;
                  else if (curChar == 33)
                     jjstateSet[jjnewStateCnt++] = 5;
                  else if (curChar == 60)
                     jjstateSet[jjnewStateCnt++] = 3;
                  else if (curChar == 61)
                     jjstateSet[jjnewStateCnt++] = 1;
                  if (curChar == 46)
                     { jjCheckNAdd(20); }
                  else if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 16;
                  else if (curChar == 38)
                  {
                     if (kind > 67)
                        kind = 67;
                  }
                  else if (curChar == 61)
                  {
                     if (kind > 59)
                        kind = 59;
                  }
                  break;
               case 53:
                  if (curChar == 62)
                     jjstateSet[jjnewStateCnt++] = 58;
                  else if (curChar == 60)
                     jjstateSet[jjnewStateCnt++] = 56;
                  else if (curChar == 38)
                  {
                     if (kind > 77)
                        kind = 77;
                  }
                  break;
               case 62:
                  if (curChar == 37)
                     jjstateSet[jjnewStateCnt++] = 63;
                  if (curChar == 37)
                     { jjCheckNAdd(61); }
                  break;
               case 248:
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(40, 41); }
                  else if (curChar == 45)
                  {
                     if (kind > 127)
                        kind = 127;
                  }
                  else if (curChar == 43)
                  {
                     if (kind > 127)
                        kind = 127;
                  }
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(38, 39); }
                  break;
               case 249:
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(44, 39); }
                  else if (curChar == 43)
                  {
                     if (kind > 127)
                        kind = 127;
                  }
                  else if (curChar == 45)
                  {
                     if (kind > 127)
                        kind = 127;
                  }
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(43, 41); }
                  break;
               case 1:
                  if (curChar == 61 && kind > 59)
                     kind = 59;
                  break;
               case 2:
                  if (curChar == 61)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 3:
                  if (curChar == 62 && kind > 61)
                     kind = 61;
                  break;
               case 4:
                  if (curChar == 60)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 5:
                  if (curChar == 61 && kind > 61)
                     kind = 61;
                  break;
               case 6:
                  if (curChar == 33)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 11:
                  if (curChar == 47 && kind > 66)
                     kind = 66;
                  break;
               case 13:
               case 14:
                  if (curChar == 38 && kind > 67)
                     kind = 67;
                  break;
               case 15:
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 17:
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 18:
                  if ((0x8c00c09800000000L & l) != 0L && kind > 79)
                     kind = 79;
                  break;
               case 19:
                  if (curChar == 46)
                     { jjCheckNAdd(20); }
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 82)
                     kind = 82;
                  { jjCheckNAddTwoStates(20, 21); }
                  break;
               case 22:
                  if ((0x280000000000L & l) != 0L)
                     { jjCheckNAdd(23); }
                  break;
               case 23:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 82)
                     kind = 82;
                  { jjCheckNAdd(23); }
                  break;
               case 25:
                  if (curChar == 46)
                     { jjCheckNAdd(26); }
                  break;
               case 26:
                  if ((0x3000000000000L & l) == 0L)
                     break;
                  if (kind > 119)
                     kind = 119;
                  { jjCheckNAdd(26); }
                  break;
               case 28:
                  if (curChar == 46)
                     { jjCheckNAdd(29); }
                  break;
               case 29:
                  if ((0xff000000000000L & l) == 0L)
                     break;
                  if (kind > 120)
                     kind = 120;
                  { jjCheckNAdd(29); }
                  break;
               case 31:
                  if (curChar == 46)
                     { jjCheckNAdd(32); }
                  break;
               case 32:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 121)
                     kind = 121;
                  { jjCheckNAdd(32); }
                  break;
               case 34:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 123)
                     kind = 123;
                  { jjAddStates(16, 17); }
                  break;
               case 37:
                  if (curChar == 43)
                     { jjCheckNAddStates(12, 15); }
                  break;
               case 38:
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(38, 39); }
                  break;
               case 39:
                  if (curChar == 43 && kind > 127)
                     kind = 127;
                  break;
               case 40:
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(40, 41); }
                  break;
               case 41:
                  if (curChar == 45 && kind > 127)
                     kind = 127;
                  break;
               case 42:
                  if (curChar == 45)
                     { jjCheckNAddStates(8, 11); }
                  break;
               case 43:
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(43, 41); }
                  break;
               case 44:
                  if ((0x100002600L & l) != 0L)
                     { jjCheckNAddTwoStates(44, 39); }
                  break;
               case 46:
                  if (curChar == 43 && kind > 124)
                     kind = 124;
                  break;
               case 49:
                  if (curChar == 45 && kind > 124)
                     kind = 124;
                  break;
               case 56:
                  if (curChar == 60 && kind > 77)
                     kind = 77;
                  break;
               case 57:
                  if (curChar == 60)
                     jjstateSet[jjnewStateCnt++] = 56;
                  break;
               case 58:
                  if (curChar == 62 && kind > 77)
                     kind = 77;
                  break;
               case 59:
                  if (curChar == 62)
                     jjstateSet[jjnewStateCnt++] = 58;
                  break;
               case 63:
                  if (curChar == 37)
                     { jjCheckNAdd(61); }
                  break;
               case 64:
                  if (curChar == 37)
                     jjstateSet[jjnewStateCnt++] = 63;
                  break;
               case 67:
                  if (curChar == 38 && kind > 74)
                     kind = 74;
                  break;
               case 68:
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 67;
                  break;
               case 70:
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 69;
                  break;
               case 74:
                  if (curChar == 47 && kind > 76)
                     kind = 76;
                  break;
               case 77:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 78;
                  break;
               case 78:
                  if (curChar != 46)
                     break;
                  if (kind > 83)
                     kind = 83;
                  { jjCheckNAdd(79); }
                  break;
               case 79:
                  if (curChar != 49)
                     break;
                  if (kind > 83)
                     kind = 83;
                  { jjCheckNAdd(79); }
                  break;
               case 80:
                  if (curChar == 50)
                     jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 81:
                  if (curChar == 46)
                     { jjCheckNAdd(82); }
                  break;
               case 82:
                  if ((0x3000000000000L & l) == 0L)
                     break;
                  if (kind > 84)
                     kind = 84;
                  { jjCheckNAdd(82); }
                  break;
               case 83:
                  if (curChar == 51)
                     jjstateSet[jjnewStateCnt++] = 84;
                  break;
               case 84:
                  if (curChar == 46)
                     { jjCheckNAdd(85); }
                  break;
               case 85:
                  if ((0x7000000000000L & l) == 0L)
                     break;
                  if (kind > 85)
                     kind = 85;
                  { jjCheckNAdd(85); }
                  break;
               case 86:
                  if (curChar == 52)
                     jjstateSet[jjnewStateCnt++] = 87;
                  break;
               case 87:
                  if (curChar == 46)
                     { jjCheckNAdd(88); }
                  break;
               case 88:
                  if ((0xf000000000000L & l) == 0L)
                     break;
                  if (kind > 86)
                     kind = 86;
                  { jjCheckNAdd(88); }
                  break;
               case 89:
                  if (curChar == 53)
                     jjstateSet[jjnewStateCnt++] = 90;
                  break;
               case 90:
                  if (curChar == 46)
                     { jjCheckNAdd(91); }
                  break;
               case 91:
                  if ((0x1f000000000000L & l) == 0L)
                     break;
                  if (kind > 87)
                     kind = 87;
                  { jjCheckNAdd(91); }
                  break;
               case 92:
                  if (curChar == 54)
                     jjstateSet[jjnewStateCnt++] = 93;
                  break;
               case 93:
                  if (curChar == 46)
                     { jjCheckNAdd(94); }
                  break;
               case 94:
                  if ((0x3f000000000000L & l) == 0L)
                     break;
                  if (kind > 88)
                     kind = 88;
                  { jjCheckNAdd(94); }
                  break;
               case 95:
                  if (curChar == 55)
                     jjstateSet[jjnewStateCnt++] = 96;
                  break;
               case 96:
                  if (curChar == 46)
                     { jjCheckNAdd(97); }
                  break;
               case 97:
                  if ((0x7f000000000000L & l) == 0L)
                     break;
                  if (kind > 89)
                     kind = 89;
                  { jjCheckNAdd(97); }
                  break;
               case 98:
                  if (curChar == 56)
                     jjstateSet[jjnewStateCnt++] = 99;
                  break;
               case 99:
                  if (curChar == 46)
                     { jjCheckNAdd(100); }
                  break;
               case 100:
                  if ((0xff000000000000L & l) == 0L)
                     break;
                  if (kind > 90)
                     kind = 90;
                  { jjCheckNAdd(100); }
                  break;
               case 101:
                  if (curChar == 57)
                     jjstateSet[jjnewStateCnt++] = 102;
                  break;
               case 102:
                  if (curChar == 46)
                     { jjCheckNAdd(103); }
                  break;
               case 103:
                  if ((0x1ff000000000000L & l) == 0L)
                     break;
                  if (kind > 91)
                     kind = 91;
                  { jjCheckNAdd(103); }
                  break;
               case 104:
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 105;
                  break;
               case 105:
                  if (curChar == 46)
                     { jjCheckNAdd(106); }
                  break;
               case 106:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 92)
                     kind = 92;
                  { jjCheckNAdd(106); }
                  break;
               case 107:
               case 213:
                  if (curChar == 49)
                     { jjCheckNAdd(104); }
                  break;
               case 108:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 109;
                  break;
               case 109:
                  if (curChar == 46)
                     { jjCheckNAdd(110); }
                  break;
               case 110:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 93)
                     kind = 93;
                  { jjCheckNAdd(110); }
                  break;
               case 111:
               case 214:
                  if (curChar == 49)
                     { jjCheckNAdd(108); }
                  break;
               case 112:
                  if (curChar == 50)
                     jjstateSet[jjnewStateCnt++] = 113;
                  break;
               case 113:
                  if (curChar == 46)
                     { jjCheckNAdd(114); }
                  break;
               case 114:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 94)
                     kind = 94;
                  { jjCheckNAdd(114); }
                  break;
               case 115:
               case 215:
                  if (curChar == 49)
                     { jjCheckNAdd(112); }
                  break;
               case 116:
                  if (curChar == 51)
                     jjstateSet[jjnewStateCnt++] = 117;
                  break;
               case 117:
                  if (curChar == 46)
                     { jjCheckNAdd(118); }
                  break;
               case 118:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 95)
                     kind = 95;
                  { jjCheckNAdd(118); }
                  break;
               case 119:
               case 216:
                  if (curChar == 49)
                     { jjCheckNAdd(116); }
                  break;
               case 120:
                  if (curChar == 52)
                     jjstateSet[jjnewStateCnt++] = 121;
                  break;
               case 121:
                  if (curChar == 46)
                     { jjCheckNAdd(122); }
                  break;
               case 122:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 96)
                     kind = 96;
                  { jjCheckNAdd(122); }
                  break;
               case 123:
               case 217:
                  if (curChar == 49)
                     { jjCheckNAdd(120); }
                  break;
               case 124:
                  if (curChar == 53)
                     jjstateSet[jjnewStateCnt++] = 125;
                  break;
               case 125:
                  if (curChar == 46)
                     { jjCheckNAdd(126); }
                  break;
               case 126:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 97)
                     kind = 97;
                  { jjCheckNAdd(126); }
                  break;
               case 127:
               case 218:
                  if (curChar == 49)
                     { jjCheckNAdd(124); }
                  break;
               case 128:
                  if (curChar == 54)
                     jjstateSet[jjnewStateCnt++] = 129;
                  break;
               case 129:
                  if (curChar == 46)
                     { jjCheckNAdd(130); }
                  break;
               case 130:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 98)
                     kind = 98;
                  { jjCheckNAdd(130); }
                  break;
               case 131:
               case 219:
                  if (curChar == 49)
                     { jjCheckNAdd(128); }
                  break;
               case 132:
                  if (curChar == 55)
                     jjstateSet[jjnewStateCnt++] = 133;
                  break;
               case 133:
                  if (curChar == 46)
                     { jjCheckNAdd(134); }
                  break;
               case 134:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 99)
                     kind = 99;
                  { jjCheckNAdd(134); }
                  break;
               case 135:
               case 220:
                  if (curChar == 49)
                     { jjCheckNAdd(132); }
                  break;
               case 136:
                  if (curChar == 56)
                     jjstateSet[jjnewStateCnt++] = 137;
                  break;
               case 137:
                  if (curChar == 46)
                     { jjCheckNAdd(138); }
                  break;
               case 138:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 100)
                     kind = 100;
                  { jjCheckNAdd(138); }
                  break;
               case 139:
               case 221:
                  if (curChar == 49)
                     { jjCheckNAdd(136); }
                  break;
               case 140:
                  if (curChar == 57)
                     jjstateSet[jjnewStateCnt++] = 141;
                  break;
               case 141:
                  if (curChar == 46)
                     { jjCheckNAdd(142); }
                  break;
               case 142:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 101)
                     kind = 101;
                  { jjCheckNAdd(142); }
                  break;
               case 143:
               case 222:
                  if (curChar == 49)
                     { jjCheckNAdd(140); }
                  break;
               case 144:
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 145;
                  break;
               case 145:
                  if (curChar == 46)
                     { jjCheckNAdd(146); }
                  break;
               case 146:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 102)
                     kind = 102;
                  { jjCheckNAdd(146); }
                  break;
               case 147:
               case 223:
                  if (curChar == 50)
                     { jjCheckNAdd(144); }
                  break;
               case 148:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 149;
                  break;
               case 149:
                  if (curChar == 46)
                     { jjCheckNAdd(150); }
                  break;
               case 150:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 103)
                     kind = 103;
                  { jjCheckNAdd(150); }
                  break;
               case 151:
               case 224:
                  if (curChar == 50)
                     { jjCheckNAdd(148); }
                  break;
               case 152:
                  if (curChar == 50)
                     jjstateSet[jjnewStateCnt++] = 153;
                  break;
               case 153:
                  if (curChar == 46)
                     { jjCheckNAdd(154); }
                  break;
               case 154:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 104)
                     kind = 104;
                  { jjCheckNAdd(154); }
                  break;
               case 155:
               case 225:
                  if (curChar == 50)
                     { jjCheckNAdd(152); }
                  break;
               case 156:
                  if (curChar == 51)
                     jjstateSet[jjnewStateCnt++] = 157;
                  break;
               case 157:
                  if (curChar == 46)
                     { jjCheckNAdd(158); }
                  break;
               case 158:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 105)
                     kind = 105;
                  { jjCheckNAdd(158); }
                  break;
               case 159:
               case 226:
                  if (curChar == 50)
                     { jjCheckNAdd(156); }
                  break;
               case 160:
                  if (curChar == 52)
                     jjstateSet[jjnewStateCnt++] = 161;
                  break;
               case 161:
                  if (curChar == 46)
                     { jjCheckNAdd(162); }
                  break;
               case 162:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 106)
                     kind = 106;
                  { jjCheckNAdd(162); }
                  break;
               case 163:
               case 227:
                  if (curChar == 50)
                     { jjCheckNAdd(160); }
                  break;
               case 164:
                  if (curChar == 53)
                     jjstateSet[jjnewStateCnt++] = 165;
                  break;
               case 165:
                  if (curChar == 46)
                     { jjCheckNAdd(166); }
                  break;
               case 166:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 107)
                     kind = 107;
                  { jjCheckNAdd(166); }
                  break;
               case 167:
               case 228:
                  if (curChar == 50)
                     { jjCheckNAdd(164); }
                  break;
               case 168:
                  if (curChar == 54)
                     jjstateSet[jjnewStateCnt++] = 169;
                  break;
               case 169:
                  if (curChar == 46)
                     { jjCheckNAdd(170); }
                  break;
               case 170:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 108)
                     kind = 108;
                  { jjCheckNAdd(170); }
                  break;
               case 171:
               case 229:
                  if (curChar == 50)
                     { jjCheckNAdd(168); }
                  break;
               case 172:
                  if (curChar == 55)
                     jjstateSet[jjnewStateCnt++] = 173;
                  break;
               case 173:
                  if (curChar == 46)
                     { jjCheckNAdd(174); }
                  break;
               case 174:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 109)
                     kind = 109;
                  { jjCheckNAdd(174); }
                  break;
               case 175:
               case 230:
                  if (curChar == 50)
                     { jjCheckNAdd(172); }
                  break;
               case 176:
                  if (curChar == 56)
                     jjstateSet[jjnewStateCnt++] = 177;
                  break;
               case 177:
                  if (curChar == 46)
                     { jjCheckNAdd(178); }
                  break;
               case 178:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 110)
                     kind = 110;
                  { jjCheckNAdd(178); }
                  break;
               case 179:
               case 231:
                  if (curChar == 50)
                     { jjCheckNAdd(176); }
                  break;
               case 180:
                  if (curChar == 57)
                     jjstateSet[jjnewStateCnt++] = 181;
                  break;
               case 181:
                  if (curChar == 46)
                     { jjCheckNAdd(182); }
                  break;
               case 182:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 111)
                     kind = 111;
                  { jjCheckNAdd(182); }
                  break;
               case 183:
               case 232:
                  if (curChar == 50)
                     { jjCheckNAdd(180); }
                  break;
               case 184:
                  if (curChar == 48)
                     jjstateSet[jjnewStateCnt++] = 185;
                  break;
               case 185:
                  if (curChar == 46)
                     { jjCheckNAdd(186); }
                  break;
               case 186:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 112)
                     kind = 112;
                  { jjCheckNAdd(186); }
                  break;
               case 187:
               case 233:
                  if (curChar == 51)
                     { jjCheckNAdd(184); }
                  break;
               case 188:
                  if (curChar == 49)
                     jjstateSet[jjnewStateCnt++] = 189;
                  break;
               case 189:
                  if (curChar == 46)
                     { jjCheckNAdd(190); }
                  break;
               case 190:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 113)
                     kind = 113;
                  { jjCheckNAdd(190); }
                  break;
               case 191:
               case 234:
                  if (curChar == 51)
                     { jjCheckNAdd(188); }
                  break;
               case 192:
                  if (curChar == 50)
                     jjstateSet[jjnewStateCnt++] = 193;
                  break;
               case 193:
                  if (curChar == 46)
                     { jjCheckNAdd(194); }
                  break;
               case 194:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 114)
                     kind = 114;
                  { jjCheckNAdd(194); }
                  break;
               case 195:
               case 235:
                  if (curChar == 51)
                     { jjCheckNAdd(192); }
                  break;
               case 196:
                  if (curChar == 51)
                     jjstateSet[jjnewStateCnt++] = 197;
                  break;
               case 197:
                  if (curChar == 46)
                     { jjCheckNAdd(198); }
                  break;
               case 198:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 115)
                     kind = 115;
                  { jjCheckNAdd(198); }
                  break;
               case 199:
               case 236:
                  if (curChar == 51)
                     { jjCheckNAdd(196); }
                  break;
               case 200:
                  if (curChar == 52)
                     jjstateSet[jjnewStateCnt++] = 201;
                  break;
               case 201:
                  if (curChar == 46)
                     { jjCheckNAdd(202); }
                  break;
               case 202:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 116)
                     kind = 116;
                  { jjCheckNAdd(202); }
                  break;
               case 203:
               case 237:
                  if (curChar == 51)
                     { jjCheckNAdd(200); }
                  break;
               case 204:
                  if (curChar == 53)
                     jjstateSet[jjnewStateCnt++] = 205;
                  break;
               case 205:
                  if (curChar == 46)
                     { jjCheckNAdd(206); }
                  break;
               case 206:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 117)
                     kind = 117;
                  { jjCheckNAdd(206); }
                  break;
               case 207:
               case 238:
                  if (curChar == 51)
                     { jjCheckNAdd(204); }
                  break;
               case 208:
                  if (curChar == 54)
                     jjstateSet[jjnewStateCnt++] = 209;
                  break;
               case 209:
                  if (curChar == 46)
                     { jjCheckNAdd(210); }
                  break;
               case 210:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 118)
                     kind = 118;
                  { jjCheckNAdd(210); }
                  break;
               case 211:
               case 239:
                  if (curChar == 51)
                     { jjCheckNAdd(208); }
                  break;
               case 240:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 82)
                     kind = 82;
                  { jjCheckNAddStates(0, 7); }
                  break;
               case 241:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(241, 19); }
                  break;
               case 242:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 82)
                     kind = 82;
                  { jjCheckNAddTwoStates(242, 21); }
                  break;
               case 243:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(243, 244); }
                  break;
               case 245:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(245, 246); }
                  break;
               case 247:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 122)
                     kind = 122;
                  jjstateSet[jjnewStateCnt++] = 247;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 63);
         do
         {
            switch(jjstateSet[--i])
            {
               case 66:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 74;
                  else if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 72;
                  if (curChar == 124)
                  {
                     if (kind > 76)
                        kind = 76;
                  }
                  break;
               case 0:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 123)
                        kind = 123;
                     { jjCheckNAddTwoStates(34, 36); }
                  }
                  else if ((0x110000001L & l) != 0L)
                  {
                     if (kind > 79)
                        kind = 79;
                  }
                  else if (curChar == 126)
                     { jjAddStates(18, 23); }
                  else if (curChar == 91)
                     { jjAddStates(24, 25); }
                  else if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 9;
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 31;
                  else if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 28;
                  else if ((0x400000004L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 25;
                  else if (curChar == 64)
                     { jjAddStates(26, 30); }
                  else if (curChar == 100)
                     { jjAddStates(31, 32); }
                  else if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 11;
                  else if (curChar == 124)
                  {
                     if (kind > 66)
                        kind = 66;
                  }
                  else if (curChar == 126)
                     jjstateSet[jjnewStateCnt++] = 5;
                  if (curChar == 66)
                     { jjCheckNAddStates(33, 68); }
                  else if (curChar == 98)
                     { jjCheckNAddStates(69, 104); }
                  break;
               case 53:
                  if (curChar == 124)
                  {
                     if (kind > 77)
                        kind = 77;
                  }
                  else if (curChar == 94)
                  {
                     if (kind > 77)
                        kind = 77;
                  }
                  break;
               case 7:
                  if (curChar == 126)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 8:
               case 9:
                  if (curChar == 124 && kind > 66)
                     kind = 66;
                  break;
               case 10:
                  if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 12:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 16:
                  if (curChar == 92 && kind > 67)
                     kind = 67;
                  break;
               case 18:
                  if ((0x110000001L & l) != 0L && kind > 79)
                     kind = 79;
                  break;
               case 21:
                  if ((0x2000000020L & l) != 0L)
                     { jjAddStates(105, 106); }
                  break;
               case 24:
                  if ((0x400000004L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 27:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 30:
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 32:
                  if ((0x7e0000007eL & l) == 0L)
                     break;
                  if (kind > 121)
                     kind = 121;
                  jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 33:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 123)
                     kind = 123;
                  { jjCheckNAddTwoStates(34, 36); }
                  break;
               case 35:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 123)
                     kind = 123;
                  { jjCheckNAddTwoStates(34, 35); }
                  break;
               case 36:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 123)
                     kind = 123;
                  { jjCheckNAddStates(107, 109); }
                  break;
               case 45:
                  if (curChar == 100)
                     { jjAddStates(31, 32); }
                  break;
               case 47:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 48:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 50:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 49;
                  break;
               case 51:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 50;
                  break;
               case 52:
                  if (curChar == 64)
                     { jjAddStates(26, 30); }
                  break;
               case 54:
                  if (curChar == 94)
                     kind = 77;
                  break;
               case 55:
                  if (curChar == 124 && kind > 77)
                     kind = 77;
                  break;
               case 60:
                  if (curChar == 91)
                     { jjAddStates(24, 25); }
                  break;
               case 61:
                  if (curChar == 93)
                     kind = 60;
                  break;
               case 65:
                  if (curChar == 126)
                     { jjAddStates(18, 23); }
                  break;
               case 69:
                  if (curChar == 92 && kind > 74)
                     kind = 74;
                  break;
               case 71:
               case 72:
                  if (curChar == 124 && kind > 76)
                     kind = 76;
                  break;
               case 73:
                  if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 72;
                  break;
               case 75:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 74;
                  break;
               case 76:
                  if (curChar == 98)
                     { jjCheckNAddStates(69, 104); }
                  break;
               case 110:
                  if ((0x200000002L & l) == 0L)
                     break;
                  if (kind > 93)
                     kind = 93;
                  jjstateSet[jjnewStateCnt++] = 110;
                  break;
               case 114:
                  if ((0x600000006L & l) == 0L)
                     break;
                  if (kind > 94)
                     kind = 94;
                  jjstateSet[jjnewStateCnt++] = 114;
                  break;
               case 118:
                  if ((0xe0000000eL & l) == 0L)
                     break;
                  if (kind > 95)
                     kind = 95;
                  jjstateSet[jjnewStateCnt++] = 118;
                  break;
               case 122:
                  if ((0x1e0000001eL & l) == 0L)
                     break;
                  if (kind > 96)
                     kind = 96;
                  jjstateSet[jjnewStateCnt++] = 122;
                  break;
               case 126:
                  if ((0x3e0000003eL & l) == 0L)
                     break;
                  if (kind > 97)
                     kind = 97;
                  jjstateSet[jjnewStateCnt++] = 126;
                  break;
               case 130:
                  if ((0x7e0000007eL & l) == 0L)
                     break;
                  if (kind > 98)
                     kind = 98;
                  jjstateSet[jjnewStateCnt++] = 130;
                  break;
               case 134:
                  if ((0xfe000000feL & l) == 0L)
                     break;
                  if (kind > 99)
                     kind = 99;
                  jjstateSet[jjnewStateCnt++] = 134;
                  break;
               case 138:
                  if ((0x1fe000001feL & l) == 0L)
                     break;
                  if (kind > 100)
                     kind = 100;
                  jjstateSet[jjnewStateCnt++] = 138;
                  break;
               case 142:
                  if ((0x3fe000003feL & l) == 0L)
                     break;
                  if (kind > 101)
                     kind = 101;
                  jjstateSet[jjnewStateCnt++] = 142;
                  break;
               case 146:
                  if ((0x7fe000007feL & l) == 0L)
                     break;
                  if (kind > 102)
                     kind = 102;
                  jjstateSet[jjnewStateCnt++] = 146;
                  break;
               case 150:
                  if ((0xffe00000ffeL & l) == 0L)
                     break;
                  if (kind > 103)
                     kind = 103;
                  jjstateSet[jjnewStateCnt++] = 150;
                  break;
               case 154:
                  if ((0x1ffe00001ffeL & l) == 0L)
                     break;
                  if (kind > 104)
                     kind = 104;
                  jjstateSet[jjnewStateCnt++] = 154;
                  break;
               case 158:
                  if ((0x3ffe00003ffeL & l) == 0L)
                     break;
                  if (kind > 105)
                     kind = 105;
                  jjstateSet[jjnewStateCnt++] = 158;
                  break;
               case 162:
                  if ((0x7ffe00007ffeL & l) == 0L)
                     break;
                  if (kind > 106)
                     kind = 106;
                  jjstateSet[jjnewStateCnt++] = 162;
                  break;
               case 166:
                  if ((0xfffe0000fffeL & l) == 0L)
                     break;
                  if (kind > 107)
                     kind = 107;
                  jjstateSet[jjnewStateCnt++] = 166;
                  break;
               case 170:
                  if ((0x1fffe0001fffeL & l) == 0L)
                     break;
                  if (kind > 108)
                     kind = 108;
                  jjstateSet[jjnewStateCnt++] = 170;
                  break;
               case 174:
                  if ((0x3fffe0003fffeL & l) == 0L)
                     break;
                  if (kind > 109)
                     kind = 109;
                  jjstateSet[jjnewStateCnt++] = 174;
                  break;
               case 178:
                  if ((0x7fffe0007fffeL & l) == 0L)
                     break;
                  if (kind > 110)
                     kind = 110;
                  jjstateSet[jjnewStateCnt++] = 178;
                  break;
               case 182:
                  if ((0xffffe000ffffeL & l) == 0L)
                     break;
                  if (kind > 111)
                     kind = 111;
                  jjstateSet[jjnewStateCnt++] = 182;
                  break;
               case 186:
                  if ((0x1ffffe001ffffeL & l) == 0L)
                     break;
                  if (kind > 112)
                     kind = 112;
                  jjstateSet[jjnewStateCnt++] = 186;
                  break;
               case 190:
                  if ((0x3ffffe003ffffeL & l) == 0L)
                     break;
                  if (kind > 113)
                     kind = 113;
                  jjstateSet[jjnewStateCnt++] = 190;
                  break;
               case 194:
                  if ((0x7ffffe007ffffeL & l) == 0L)
                     break;
                  if (kind > 114)
                     kind = 114;
                  jjstateSet[jjnewStateCnt++] = 194;
                  break;
               case 198:
                  if ((0xfffffe00fffffeL & l) == 0L)
                     break;
                  if (kind > 115)
                     kind = 115;
                  jjstateSet[jjnewStateCnt++] = 198;
                  break;
               case 202:
                  if ((0x1fffffe01fffffeL & l) == 0L)
                     break;
                  if (kind > 116)
                     kind = 116;
                  jjstateSet[jjnewStateCnt++] = 202;
                  break;
               case 206:
                  if ((0x3fffffe03fffffeL & l) == 0L)
                     break;
                  if (kind > 117)
                     kind = 117;
                  jjstateSet[jjnewStateCnt++] = 206;
                  break;
               case 210:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 118)
                     kind = 118;
                  jjstateSet[jjnewStateCnt++] = 210;
                  break;
               case 212:
                  if (curChar == 66)
                     { jjCheckNAddStates(33, 68); }
                  break;
               case 244:
                  if (curChar == 95)
                     jjstateSet[jjnewStateCnt++] = 245;
                  break;
               case 246:
                  if (curChar == 95)
                     jjstateSet[jjnewStateCnt++] = 247;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 63);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 248 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static  int[] jjnextStates = {
   241, 19, 242, 21, 243, 244, 245, 246, 43, 41, 44, 39, 38, 39, 40, 41, 
   35, 34, 66, 68, 70, 71, 73, 75, 62, 64, 53, 54, 55, 57, 59, 48, 
   51, 77, 80, 83, 86, 89, 92, 95, 98, 101, 213, 214, 215, 216, 217, 218, 
   219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 
   235, 236, 237, 238, 239, 77, 80, 83, 86, 89, 92, 95, 98, 101, 107, 111, 
   115, 119, 123, 127, 131, 135, 139, 143, 147, 151, 155, 159, 163, 167, 171, 175, 
   179, 183, 187, 191, 195, 199, 203, 207, 211, 22, 23, 34, 35, 36, 
};

public static  String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, "\50", "\51", "\53", "\55", "\52", "\57", "\136", 
"\136\136", "\43", "\41", "\45", "\54", "\73", null, null, null, "\74", "\74\75", "\76", 
"\76\75", null, null, "\176", "\100\176", "\55\55\76", "\74\55\55", "\55\57\76", 
"\74\57\55", null, "\74\55\76", null, null, "\50\53\51", null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, "\133", 
"\135", null, null, };
protected Token jjFillToken()
{
    Token t;
    String curTokenImage;
    int beginLine;
    int endLine;
    int beginColumn;
    int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

public Token getNextToken() 
{
  Token matchedToken;
  int curPos;

   for (; ; ) {
      try {
         curChar = input_stream.BeginToken();
      } catch (java.io.IOException e) {
         jjmatchedKind = 0;
         jjmatchedPos = -1;
         matchedToken = jjFillToken();
         return matchedToken;
      }

      try {
         input_stream.backup(0);
         while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
            curChar = input_stream.BeginToken();
      } catch (java.io.IOException e1) {
         continue;
      }
      jjmatchedKind = 0x7fffffff;
      jjmatchedPos = 0;
      curPos = jjMoveStringLiteralDfa0_0();
      if (jjmatchedPos == 0 && jjmatchedKind > 128) {
         jjmatchedKind = 128;
      }
      if (jjmatchedKind != 0x7fffffff) {
         if (jjmatchedPos + 1 < curPos)
            input_stream.backup(curPos - jjmatchedPos - 1);
         if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 63))) != 0L) {
            matchedToken = jjFillToken();
            return matchedToken;
         } else {
            continue;
         }
      }
      int error_line = input_stream.getEndLine();
      int error_column = input_stream.getEndColumn();
      String error_after = null;
      boolean EOFSeen = false;
      try {
         input_stream.readChar();
         input_stream.backup(1);
      } catch (java.io.IOException e1) {
         EOFSeen = true;
         error_after = curPos <= 1 ? "" : input_stream.GetImage();
         if (curChar == '\n' || curChar == '\r') {
            error_line++;
            error_column = 0;
         } else
            error_column++;
      }
      if (!EOFSeen) {
         input_stream.backup(1);
         error_after = curPos <= 1 ? "" : input_stream.GetImage();
      }
      throw new TokenMgrError(EOFSeen, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    public SyntaxCheckerTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  public SyntaxCheckerTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  public void ReInit(SimpleCharStream stream)
  {
    jjmatchedPos = jjnewStateCnt = 0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 248; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  public void ReInit(SimpleCharStream stream, int lexState)
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  public void SwitchTo(int lexState)
  {
    if (lexState != 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }

public static  String[] lexStateNames = {
   "DEFAULT",
};
static  long[] jjtoToken = {
   0xffffc00000000001L, 0xfffffffffffcffffL, 0x1L, 
};
static  long[] jjtoSkip = {
   0x1eL, 0x0L, 0x0L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[248];
    private final int[] jjstateSet = new int[2 * 248];

    
    protected char curChar;
}
