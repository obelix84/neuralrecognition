function X=find_concentr(Z)
[n m]= size(Z);
X1=zeros([1 n]);
for i=1:n
    x1=concentrate(Z(i,:));
    X1(i)=x1;
end
X=X1;

function x=concentrate(str)
x1=0;
while (numel(str)~=0)
    [m n]=size(str);
    a1=str(1:n-1);
    a2=str(2:n);
    c1=criteria(a1);
    c2=criteria(a2);
    if (c1<c2)
        str=a2;
        x1=x1+1;
    end
    if (c1>c2)  
        str=a1;
    end
    if (c1==c2)
        str=a2;
        x1=x1+1;
    end
end
x=x1;


function cr=criteria(a)
[n m]=size(a);
c=0;
for j=1:m
    if (a(j)==1)
        c=c+1;
    end
end
cr=c;
