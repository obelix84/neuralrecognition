function BW2=skeletonization(BW)
BW1=bwmorph(BW,'thin');% ���������
x=0
size(BW)
size(BW1)
while true % ���� ���� ����� �� ����� "��������������"
    BW=BW1;
    BW1=bwmorph(BW,'thin');
    if BW1==BW
        break;
    end
end
BW2=BW1;