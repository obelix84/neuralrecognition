function waveletedge(fname,n,theta1,theta2)
I=imread(fname);
J=rgb2gray(I);
[j k]=size(J)
CJ=zeros(j,k);
for t=1:1:j
    C=cwt(double(J(t,:)),1:1:n,'cgau1');
    CJ(t,:)=C(n,:);
end
CJ1=zeros(j,k);
for t=1:1:k
    C=cwt(double(J(:,t)),1:1:n,'cgau1');
    CJ1(:,t)=C(n,:);
end

reCJ=real(CJ);
reCJ1=real(CJ1);
re_res=reCJ+reCJ1;
maxx_real=max(max(re_res));
re_res=re_res./maxx_real;
%figure, imshow(re_res);
imCJ=imag(CJ);
imCJ1=imag(CJ1);
%imCJ=zeros(size(imCJ1));
%figure, imshow(imag(CJ1));
maxx=max(max(imCJ))


maxx1=max(max(imCJ1))
%for t=1:1:j
%    for t1=1:1:k
%        if (imCJ(t,t1)<maxx*theta1) | (imCJ(t,t1)>maxx*theta2)
%            imCJ(t,t1)=0;
%        end
%    end
%end
res_kv=sqrt((imCJ.*imCJ)+(imCJ1.*imCJ1));
resmax=max(max(res_kv));
res_kv=res_kv./resmax;
figure, imshow(res_kv);
maxJ=max(max(J));
J1=double(double(J).*res_kv)
figure, imshow(double(J));
maxJ1=max(max(J1));
J1=J1./maxJ1;
figure, imshow(J1);
%figure, imshow(J./maxJ);
%figure, imshow(double(J));
imCJ=(imCJ./maxx)
imCJ1=(imCJ1./maxx1);

Vertical_hist=zeros(k);
Gorizontal_hist=zeros(j);
for t=1:1:k
    Vertical_hist(t)=sum(imCJ1(:,t));
end
for t=1:1:j
    Gorizontal_hist(t)=sum(imCJ(t,:));
end
%figure, hist(Vertical_hist,1:1:k);
%plot(1:1:k,Gorizontal_hist);
%plot(1:1:j,Vertical_hist);

res=imCJ+imCJ1;
res_minus=zeros(j,k);
res_plus=zeros(j,k);
for t=1:1:j
    for t1=1:1:k
        if (res(t,t1)<=0)
            res_minus(t,t1)=res(t,t1);
        else
            res_plus(t,t1)=res(t,t1);
        end
    end
end
maxxres=max(max(res));
res=res./maxxres;
%figure, surf(res(1:5:end, 1:5:end)), zlim([-1 1]);
%figure, imshow(abs(res));
%figure, imshow(abs(res_plus./maxxres));
%figure, imshow(abs(res_minus./maxxres));


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   Local Function : cannyFindLocalMaxima
%
function idxLocalMax = cannyFindLocalMaxima(direction,ix,iy,mag)
%
% This sub-function helps with the non-maximum supression in the Canny
% edge detector.  The input parameters are:
% 
%   direction - the index of which direction the gradient is pointing, 
%               read from the diagram below. direction is 1, 2, 3, or 4.
%   ix        - input image filtered by derivative of gaussian along x 
%   iy        - input image filtered by derivative of gaussian along y
%   mag       - the gradient magnitude image
%
%    there are 4 cases:
%
%                         The X marks the pixel in question, and each
%         3     2         of the quadrants for the gradient vector
%       O----0----0       fall into two cases, divided by the 45 
%     4 |         | 1     degree line.  In one case the gradient
%       |         |       vector is more horizontal, and in the other
%       O    X    O       it is more vertical.  There are eight 
%       |         |       divisions, but for the non-maximum supression  
%    (1)|         |(4)    we are only worried about 4 of them since we 
%       O----O----O       use symmetric points about the center pixel.
%        (2)   (3)        


[m,n] = size(mag);

% Find the indices of all points whose gradient (specified by the 
% vector (ix,iy)) is going in the direction we're looking at.  

switch direction
 case 1
  idx = find((iy<=0 & ix>-iy)  | (iy>=0 & ix<-iy));
 case 2
  idx = find((ix>0 & -iy>=ix)  | (ix<0 & -iy<=ix));
 case 3
  idx = find((ix<=0 & ix>iy) | (ix>=0 & ix<iy));
 case 4
  idx = find((iy<0 & ix<=iy) | (iy>0 & ix>=iy));
end

% Exclude the exterior pixels
if ~isempty(idx)
  v = mod(idx,m);
  extIdx = find(v==1 | v==0 | idx<=m | (idx>(n-1)*m));
  idx(extIdx) = [];
end

ixv = ix(idx);  
iyv = iy(idx);   
gradmag = mag(idx);

% Do the linear interpolations for the interior pixels
switch direction
 case 1
  d = abs(iyv./ixv);
  gradmag1 = mag(idx+m).*(1-d) + mag(idx+m-1).*d; 
  gradmag2 = mag(idx-m).*(1-d) + mag(idx-m+1).*d; 
 case 2
  d = abs(ixv./iyv);
  gradmag1 = mag(idx-1).*(1-d) + mag(idx+m-1).*d; 
  gradmag2 = mag(idx+1).*(1-d) + mag(idx-m+1).*d; 
 case 3
  d = abs(ixv./iyv);
  gradmag1 = mag(idx-1).*(1-d) + mag(idx-m-1).*d; 
  gradmag2 = mag(idx+1).*(1-d) + mag(idx+m+1).*d; 
 case 4
  d = abs(iyv./ixv);
  gradmag1 = mag(idx-m).*(1-d) + mag(idx-m-1).*d; 
  gradmag2 = mag(idx+m).*(1-d) + mag(idx+m+1).*d; 
end
idxLocalMax = idx(gradmag>=gradmag1 & gradmag>=gradmag2); 

