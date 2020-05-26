%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%разрезает номер на символы 
%по сумме столбцов
%на вход подается номерной знак с выделенным краем
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function S=CutIntoPeaces(num)
konst=3;
[n m]=size(num);
%надо бы порезать снизу и сверху... может заодно и чего не нужное отрежем
vert_stat=zeros(n,1);
for i=1:n
    vert_stat(i)=sum(num(i,1:m));
end
bar(vert_stat);
if (mod(n,2)==0)
    centr=n/2;
else
    centr=(n-1)/2;
end
ind=find(vert_stat==0);
down=min(find(ind>centr));
down=ind(down);
up=max(find(ind<centr));
up=ind(up);
vert_size=down-up;
num_new=zeros(vert_size,m);
num_new=num(up:down,:);
num=num_new;
statistic=zeros(m,1);
[n m]=size(num);
for i=1:m
    statistic(i)=sum(num(1:n,i));
end
bar(statistic);
ind=find(statistic>0);
k=size(ind)-1;
c=0;
for i=1:k
    delta=ind(i+1)-ind(i);
    if (delta>1)
        c=c+1;
    end
end
parts=zeros(c+2,1);
parts(1)=ind(1);
parts(c+2)=ind(end);
x=2;
for i=1:k
    delta=ind(i+1)-ind(i);
    if (delta>1)
        parts(x)=ind(i);
        x=x+1;
    end
end
[a a1]=size(parts);
t=zeros(a-1,1);
start=zeros(a-1,1);
for i=2:a
    j=parts(i);
   while (statistic(j)>0)
            j=j-1;
        if (j==0)
            break;
        end
    end
    start(i-1)=j;
    t(i-1)=parts(i)-j;
end
% удаляем мусор среди выделенных элементов
[q w]=size(t);
M=sum(t);
M=M/q;
por=M/konst;
c=0;
for i=1:q
    if (t>por)
        c=c+1;
    end
end
m=max(t);
S=zeros(n,m,c);
z=0;
for i=1:a-1
    if(t(i)>por)
        z=z+1;    
        for j=1:n
            for k=1:t(i)
                S(j,k,z)=num(j,k+start(i));
            end
        end
     end
end