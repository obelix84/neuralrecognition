function ret=electrostatic(picname)
I=imread(picname);
I=rgb2gray(I);
figure,imshow(I);
[n m]=size(I);
%��� ���������� ��������
k=2*(n-m)^2
%������� �������� ������������� �/� ����
new=zeros(size(I));
%��������� �� ���� �������
%� ������ ����� ������������
for i=1:n
    for j=1:m
        i
        summ=0;
        for i1=1:n
             for j1=1:m
                 norm=( ( (i-i1)^2 + (j-j1)^2 ) );
                 ai1j1=0;
                 if (norm > 0) & (norm < 3)
                     ai1j1=k/sqrt(norm);
                 end;
                 summ=summ+ai1j1;
             end
        end
        summ
        new(i,j)=summ;
    end
end
maxx=max(max(new));
figure,imshow(new/maxx);